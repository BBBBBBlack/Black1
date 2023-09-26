package com.example.test01_coll.service;

import com.example.test01_coll.domain.entity.Bucket;
import com.example.test01_coll.domain.entity.Result;

import javax.servlet.http.HttpServletResponse;

public interface GetService {
    Result<Bucket> getBucket(String bucketId);

    void getFile(String bucketId, String fileName, Integer version,HttpServletResponse response);
}
