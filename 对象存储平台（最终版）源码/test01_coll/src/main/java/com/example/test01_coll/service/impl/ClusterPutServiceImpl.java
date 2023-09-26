package com.example.test01_coll.service.impl;

import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.domain.entity.*;
import com.example.test01_coll.domain.entity.Factory.FeignClientFactory;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.feign.TestFeign;
import com.example.test01_coll.service.ClusterPutService;
import com.example.test01_coll.service.PutService;
import com.example.test01_coll.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ClusterPutServiceImpl implements ClusterPutService {

    @Autowired
    PutService putService;

    @Autowired
    BucketCache bucketCache;

    @Autowired
    FeignClientFactory clientFactory;

    @Autowired
    ReedSolomonEncoder solomonEncoder;
    @Autowired
    ReedSolomonDecoder solomonDecoder;
    @Autowired
    AsyncTask asyncTask;

    @Override
    public Result<String> uploadSimple(SimpleFile simpleFile) {
        String bucketId = simpleFile.getBucketId();
        String filename = simpleFile.getMultipartFile().getOriginalFilename();
        String socket = ClusterUtil.distributeSlot(bucketId, filename);
        if ("localhost".equals(socket)) {
            return putService.uploadSimple(simpleFile);
        } else if (socket != null) {
            return clientFactory
                    .getFeignClient(TestFeign.class, "http://" + socket)
                    .uploadSimple(simpleFile.getMultipartFile(), simpleFile.getOriginMD5(),
                            bucketId, simpleFile.getIsZip());
        }
        return new Result<>(200, "找不到路径");
    }

    @Override
    public Result shardPreparation(ShardMessage message, String originMd5) {
        String bucketId = message.getBucketId();
        String fileName = message.getFileName();
        String socket = ClusterUtil.distributeSlot(bucketId, fileName);
        if ("localhost".equals(socket)) {
            return putService.shardPreparation(message, originMd5);
        } else if (socket != null) {
            return clientFactory
                    .getFeignClient(TestFeign.class, "http://" + socket)
                    .shardPreparation(fileName, message.getShardNum(), message.getShardSize(), bucketId, message.getIsZip(), originMd5);
        }
        return new Result<>(200, "找不到路径");
    }

    @Override
    public Result<Integer> uploadShard(ShardFile shardFile, String key) {
        try {
            String toId = JwtUtil.parseJWT(key).getSubject();
            String socket = ClusterProperty.SocketMap.get(Integer.parseInt(toId));
            if (String.valueOf(ClusterProperty.id).equals(socket)) {
                return putService.uploadShard(shardFile);
            } else if (socket != null) {
                return clientFactory
                        .getFeignClient(TestFeign.class, "http://" + socket)
                        .uploadShard(shardFile.getNo(), shardFile.getTotalMD5(), shardFile.getOwnMD5(), shardFile.getFile(), key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(200, "key解析失败");
        }
        return new Result<>(200, "找不到路径");
    }

    @Override
    public Result<Set<Integer>> checkShard(String md5, String key) {
        try {
            String toId = JwtUtil.parseJWT(key).getSubject();
            String socket = ClusterProperty.SocketMap.get(Integer.parseInt(toId));
            if (String.valueOf(ClusterProperty.id).equals(socket)) {
                return putService.checkShard(md5);
            } else if (socket != null) {
                return clientFactory
                        .getFeignClient(TestFeign.class, "http://" + socket)
                        .checkShard(md5, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(200, "key解析失败");
        }
        return new Result<>(200, "找不到路径");
    }

    @Override
    public Result coldStore(String bucketId, String fileName) {
        Integer res = asyncTask.coldStore(bucketId, fileName);
        if (res == -1) {
            return new Result(200, "存储失败");
        } else if (res == -2) {
            return new Result(200, "节点数量不足");
        } else {
            return new Result(200, "开启");
        }
    }
}
