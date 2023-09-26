package com.example.test01_coll.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.domain.entity.Factory.FeignClientFactory;
import com.example.test01_coll.domain.entity.Node;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.property.FileProperty;
import com.example.test01_coll.feign.TestFeign;
import com.example.test01_coll.mapper.BucketMapper;
import com.example.test01_coll.pojo.BucketMsg;
import com.example.test01_coll.utils.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class BucketService {

    /**
     * 三种权限
     */
    public static int READ = 1;
    public static int READ_WRITE = 2;
    public static int FORBID = 3;

    @Autowired
    BucketMapper bucketMapper;
    @Autowired
    BucketCache bucketCache;
    @Autowired
    AsyncTask asyncTask;
    @Autowired
    FeignClientFactory clientFactory;

    String realPath = FileProperty.realPath;

    String delPath = FileProperty.delPath;

    /**
     * 创建桶
     */
    public boolean addBucket(String name, int authorityType) {

        QueryWrapper<BucketMsg> wrapper = new QueryWrapper<>();

        wrapper.eq("name", name)
                .eq("creator_id", StpUtil.getLoginId().toString())
                .eq("is_delete", false);

        BucketMsg bucketMsg = bucketMapper.selectOne(wrapper);

        if (bucketMsg != null) {
            return false;
        }

        BucketMsg bucketMsg1 = new BucketMsg(name, StpUtil.getLoginId().toString(), authorityType);

        bucketMapper.insert(bucketMsg1);

        File newBucket = new File(realPath + bucketMsg1.getId());

        boolean result = newBucket.mkdirs();

        newBucket.setWritable(true, false);

        newBucket.setReadable(true, false);

        newBucket.setExecutable(true, false);


        return result;
    }

    /**
     * 更新桶的对外权限
     *
     * @param id        桶的id
     * @param authority 对外权限
     */
    public void updateAuthority(int id, int authority) {
        UpdateWrapper<BucketMsg> wrapper = new UpdateWrapper<>();

        wrapper.set("authority", authority)
                .eq("id", id);

        bucketMapper.update(null, wrapper);
    }

    /**
     * 更改bucket的名字
     *
     * @param id      桶的id
     * @param newName 桶的新名字
     */
    public void updateName(int id, String newName) {

        UpdateWrapper<BucketMsg> updateWrapper = new UpdateWrapper<>();

        updateWrapper.set("name", newName)
                .eq("id", id);

        bucketMapper.update(null, updateWrapper);
    }

    /**
     * 删除桶
     *
     * @param id 桶的id
     */
    //redis中的数据也要删除
    public void deleteBucket(int id) {
//        String loginId = StpUtil.getLoginId().toString();

//        BucketMsg bucketMsg = bucketMapper.selectById(id);

        File sourceFile = new File(realPath + id);

        File destFile = new File(delPath + id);

        sourceFile.renameTo(destFile);

        UpdateWrapper<BucketMsg> updateWrapper = new UpdateWrapper<>();

        updateWrapper.set("is_delete", true)
                .eq("id", id);
        bucketMapper.update(null, updateWrapper);

        Map<String, Integer> fileSet = bucketCache.getFileSetByName(String.valueOf(id), 1);
        fileSet.keySet().forEach(key -> {
            asyncTask.freshFileMessage(String.valueOf(id), key, 3);
        });
        if (ClusterProperty.cluster) {
            Set<Node> nodes = ClusterProperty.SocketAliveMap.keySet();
            for (Node node : nodes) {
                Integer nodeId = node.getId();
                String socket = ClusterProperty.SocketMap.get(nodeId);
                clientFactory.getFeignClient(TestFeign.class, "http://" + socket)
                        .delBucket(String.valueOf(id));
            }
        }
    }

    /**
     * 获取所有桶
     */
    public List<BucketMsg> getBuckets() {
        QueryWrapper<BucketMsg> wrapper = new QueryWrapper<>();

        wrapper.eq("creator_id", StpUtil.getLoginId().toString())
                .eq("is_delete", false);
        List<BucketMsg> bucketMsgList = bucketMapper.selectList(wrapper);
        for (BucketMsg bucketMsg : bucketMsgList) {
            bucketCache.bucketMsgScan(bucketMsg);
        }
        if (ClusterProperty.cluster) {
            for (BucketMsg msg : bucketMsgList) {
                Set<Node> nodes = ClusterProperty.SocketAliveMap.keySet();
                for (Node node : nodes) {
                    Integer nodeId = node.getId();
                    String socket = ClusterProperty.SocketMap.get(nodeId);
                    BucketMsg msg2 = clientFactory.getFeignClient(TestFeign.class, "http://" + socket)
                            .getBucketMsg(msg);
                    if (msg2 == null) {
                        continue;
                    }
                    msg.setBucketSize(msg.getBucketSize() + msg2.getBucketSize());
                    msg.setFileNum(msg.getFileNum() + msg2.getFileNum());
                }
            }
        }
        return bucketMsgList;
    }

    /**
     * 获取所有已删除的桶
     */
    public List<BucketMsg> getDeletedBuckets() {
        QueryWrapper<BucketMsg> wrapper = new QueryWrapper<>();

        wrapper.eq("creator_id", StpUtil.getLoginId().toString())
                .eq("is_delete", true);

        return bucketMapper.selectList(wrapper);
    }

    /**
     * 恢复桶
     *
     * @param id 桶的id
     */
    public boolean recoveryBucket(int id) {
        UpdateWrapper<BucketMsg> updateWrapper = new UpdateWrapper<>();

        updateWrapper.set("is_delete", false)
                .eq("id", id);

        BucketMsg bucketMsg = bucketMapper.selectById(id);

        String sourceFilePath = delPath + id;

        int suffix = 1;

        QueryWrapper<BucketMsg> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("name", bucketMsg.getName())
                .eq("creator_id", StpUtil.getLoginId().toString())
                .eq("is_delete", false);

        BucketMsg bucketMsg1 = bucketMapper.selectOne(queryWrapper);

        while (bucketMsg1 == null) {
            queryWrapper.eq("name", bucketMsg.getName() + "(" + suffix + ")")
                    .eq("creator_id", StpUtil.getLoginId().toString())
                    .eq("is_delete", false);

            bucketMsg1 = bucketMapper.selectOne(queryWrapper);
        }

        File sourceFile = new File(sourceFilePath);

        updateWrapper.set("name", bucketMsg.getName() + suffix);

        bucketMapper.update(null, updateWrapper);

        return sourceFile.renameTo(new File(realPath + id));
    }
}
