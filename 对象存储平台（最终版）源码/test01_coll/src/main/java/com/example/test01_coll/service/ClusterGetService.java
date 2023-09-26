package com.example.test01_coll.service;

import com.example.test01_coll.domain.entity.Bucket;
import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.pojo.BucketMsg;

import javax.servlet.http.HttpServletResponse;

public interface ClusterGetService {
    void getFile(String bucketId, String fileName, Integer version,HttpServletResponse response);

    Result<Bucket> getBucket(String bucketId);

    void coldGet(String bucketId, String fileName);

    BucketMsg getBucketMsg(BucketMsg bucketMsg);
}
