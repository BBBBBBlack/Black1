package com.example.test01_coll.service.impl;

import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.domain.entity.Factory.FeignClientFactory;
import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.feign.TestFeign;
import com.example.test01_coll.service.ClusterDeleteService;
import com.example.test01_coll.service.DeleteService;
import com.example.test01_coll.utils.AsyncTask;
import com.example.test01_coll.utils.ClusterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClusterDeleteServiceImpl implements ClusterDeleteService {
    @Autowired
    DeleteService deleteService;

    @Autowired
    BucketCache bucketCache;

    @Autowired
    AsyncTask asyncTask;

    @Autowired
    FeignClientFactory clientFactory;

    @Override
    public Result delFile(String bucketId, String fileName, Boolean isForever) {
        String socket = ClusterUtil.distributeSlot(bucketId, fileName);
        if ("localhost".equals(socket)) {
            return deleteService.delFile(bucketId, fileName, isForever);
        } else if (socket != null) {
            return clientFactory
                    .getFeignClient(TestFeign.class, "http://" + socket)
                    .delFile(bucketId, fileName, isForever);
        }
        return new Result<>(200, "找不到路径");
    }

    @Override
    public Result recoverFile(String bucketId, String fileName) {
        String socket = ClusterUtil.distributeSlot(bucketId, fileName);
        if ("localhost".equals(socket)) {
            return deleteService.recoverFile(bucketId, fileName);
        } else if (socket != null) {
            return clientFactory
                    .getFeignClient(TestFeign.class, "http://" + socket)
                    .recoverFile(bucketId, fileName);
        }
        return new Result<>(200, "找不到路径");
    }

    @Override
    public void delBucket(String bucketId) {
        Map<String, Integer> fileSet = bucketCache.getFileSetByName(bucketId, 1);
        fileSet.keySet().forEach(key -> {
            String fileName = key.substring(key.lastIndexOf('/') + 1);
            asyncTask.freshFileMessage(String.valueOf(bucketId), fileName, 3);
        });
    }
}
