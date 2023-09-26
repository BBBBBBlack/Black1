package com.example.test01_coll.service;

import com.example.test01_coll.domain.entity.Result;

public interface ClusterDeleteService {
    Result delFile(String bucketId, String fileName, Boolean isForever);

    Result recoverFile(String bucketId, String fileName);

    void delBucket(String bucketId);
}
