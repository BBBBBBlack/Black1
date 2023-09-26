package com.example.test01_coll.service;


import com.example.test01_coll.domain.entity.Result;

public interface DeleteService {
    Result delFile(String bucketId, String fileName, Boolean isForever);

    Result recoverFile(String bucketId, String fileName);
}
