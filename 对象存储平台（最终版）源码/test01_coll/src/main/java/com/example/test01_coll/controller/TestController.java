package com.example.test01_coll.controller;

import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.domain.entity.Factory.FeignClientFactory;
import com.example.test01_coll.utils.AsyncTask;
import com.example.test01_coll.utils.FileUtil;
import com.example.test01_coll.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    BucketCache bucketCache;

    @Autowired
    FeignClientFactory clientFactory;

    @Autowired
    AsyncTask asyncTask;

    @Autowired
    FileUtil fileUtil;

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/print")
    public String print() {
        System.out.println(Thread.currentThread().getName());
        return "hello world";
    }

}
