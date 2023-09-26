package com.example.test01_coll.service;

import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.domain.entity.ShardFile;
import com.example.test01_coll.domain.entity.ShardMessage;
import com.example.test01_coll.domain.entity.SimpleFile;

import java.util.Set;

public interface ClusterPutService {

    Result<String> uploadSimple(SimpleFile simpleFile);

    Result shardPreparation(ShardMessage message, String originMd5);

    Result<Integer> uploadShard(ShardFile shardFile, String key);

    Result<Set<Integer>> checkShard(String md5, String key);

    Result coldStore(String bucketId, String fileName);
}
