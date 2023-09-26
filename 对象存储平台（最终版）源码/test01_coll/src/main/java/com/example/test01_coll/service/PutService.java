package com.example.test01_coll.service;

import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.domain.entity.ShardFile;
import com.example.test01_coll.domain.entity.ShardMessage;
import com.example.test01_coll.domain.entity.SimpleFile;

import java.util.Set;

public interface PutService {
    Result<String> uploadSimple(SimpleFile file);

    Result shardPreparation(ShardMessage message, String Md5);

    Result<Integer> uploadShard(ShardFile shardFile);

    Result<Set<Integer>> checkShard(String md5);
}
