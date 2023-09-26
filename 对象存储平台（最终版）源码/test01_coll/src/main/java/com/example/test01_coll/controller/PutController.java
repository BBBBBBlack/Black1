package com.example.test01_coll.controller;

import com.example.test01_coll.domain.entity.Factory.FeignClientFactory;
import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.domain.entity.ShardFile;
import com.example.test01_coll.domain.entity.ShardMessage;
import com.example.test01_coll.domain.entity.SimpleFile;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.service.ClusterPutService;
import com.example.test01_coll.service.PutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/put")
public class PutController {
    @Autowired
    private PutService putService;

    @Autowired
    private ClusterPutService cPutService;

    @Autowired
    FeignClientFactory clientFactory;

    @PostMapping("/uploadSimple")
    public Result<String> uploadSimple(@RequestParam MultipartFile file,
                                       @RequestParam String originMd5,
                                       @RequestParam String bucketId,
                                       @RequestParam Boolean isZip,
                                       @RequestHeader(value = "from", required = false) String from) {
        SimpleFile simpleFile = new SimpleFile(file, originMd5, bucketId, isZip);
        if (from != null || !ClusterProperty.cluster) {
            return putService.uploadSimple(simpleFile);
        } else {
            return cPutService.uploadSimple(simpleFile);
        }
    }

    @PostMapping("/shardPreparation")
    public Result shardPreparation(@RequestParam String fileName,
                                   @RequestParam Integer shardNum,
                                   @RequestParam Long shardSize,
                                   @RequestParam String bucketId,
                                   @RequestParam Boolean isZip,
                                   @RequestParam String originMd5,
                                   @RequestHeader(value = "from", required = false) String from) {
        System.out.println(from);
        ShardMessage message =
                new ShardMessage(fileName, shardNum, shardSize, bucketId, isZip, new HashSet<>(), null);
        if (from != null || ClusterProperty.cluster) {
            return putService.shardPreparation(message, originMd5);
        } else {
            return cPutService.shardPreparation(message, originMd5);
        }

    }

    @PostMapping("/uploadShard")
    public Result<Integer> uploadShard(@RequestParam Integer no,
                                       @RequestParam String totalMd5,
                                       @RequestParam String ownMd5,
                                       @RequestParam MultipartFile file,
                                       @RequestParam String key,
                                       @RequestHeader(value = "from", required = false) String from) {
        System.out.println(from);
        ShardFile shardFile = new ShardFile(no, totalMd5, ownMd5, file);
        if (from != null || !ClusterProperty.cluster) {
            return putService.uploadShard(shardFile);
        } else {
            return cPutService.uploadShard(shardFile, key);
        }
    }

    @PostMapping("/checkShard")
    public Result<Set<Integer>> checkShard(@RequestParam String md5,
                                           @RequestParam String key,
                                           @RequestHeader(value = "from", required = false) String from) {
        System.out.println(from);
        if (from != null || !ClusterProperty.cluster) {
            return putService.checkShard(md5);
        } else {
            return cPutService.checkShard(md5, key);
        }
    }

    @PostMapping("/coldStore")
    public Result coldStore(@RequestParam String bucketId,
                            @RequestParam String fileName) {
        return cPutService.coldStore(bucketId, fileName);
    }
}
