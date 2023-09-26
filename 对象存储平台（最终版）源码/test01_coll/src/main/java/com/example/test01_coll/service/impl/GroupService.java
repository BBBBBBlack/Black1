package com.example.test01_coll.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.test01_coll.mapper.BucketMapper;
import com.example.test01_coll.mapper.GroupMapper;
import com.example.test01_coll.pojo.BucketMsg;
import com.example.test01_coll.pojo.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class GroupService {

    /**
     * 三种权限
     */
    public static int READ=1;
    public static int READ_WRITE=2;
    public static int FORBID=3;
    public static int OWNER=4;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    BucketMapper bucketMapper;


    public void addUserAuthority(int id,String email,int type){
        groupMapper.insert(new Group(email,id,type));
    }

    /**
     * 删除用户在桶中的权限
     * @param bucketId 桶id
     * @param email 用户邮箱
     */
    public void setUserAuthority(int bucketId, String email, int type){
        UpdateWrapper<Group> wrapper=new UpdateWrapper<>();

        wrapper.set("type",type)
                .eq("user_id",email)
                .eq("bucket_id", bucketId);

        groupMapper.update(null,wrapper);
    }

    /**
     * 获取用户在桶中的权限
     * @param bucketId 桶id
     */
    public int getUserAuthority(int bucketId){
        BucketMsg bucketMsg = bucketMapper.selectById(bucketId);

        QueryWrapper<Group> wrapper=new QueryWrapper<>();

        if(bucketMsg.getCreatorId().equals(StpUtil.getLoginId().toString())){
            return OWNER;
        }

        wrapper.eq("user_id", StpUtil.getLoginId().toString())
                .eq("bucket_id",bucketId);

        Group group = groupMapper.selectOne(wrapper);

        if(group==null){
            return bucketMsg.getAuthority();
        }else{
            return group.getType();
        }
    }

    public List<Map<Object,Object>> getUserList(int bucketId){
        return groupMapper.getUserList(bucketId);
    }
}
