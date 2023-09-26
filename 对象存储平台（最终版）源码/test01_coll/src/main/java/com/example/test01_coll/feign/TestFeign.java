package com.example.test01_coll.feign;

import com.example.test01_coll.domain.entity.Bucket;
import com.example.test01_coll.domain.entity.Node;
import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.pojo.BucketMsg;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@FeignClient("a-useless-name")
public interface TestFeign {
    //consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE
    @RequestMapping(value = "/get/getFile", method = RequestMethod.GET)
    Response getFile(@RequestParam("bucketId") String bucketId,
                     @RequestParam("fileName") String fileName,
                     @RequestParam(value = "version", required = false) Integer version);

    @RequestMapping(value = "/put/uploadSimple",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<String> uploadSimple(@RequestPart("file") MultipartFile file,
                                @RequestParam("originMd5") String originMd5,
                                @RequestParam("bucketId") String bucketId,
                                @RequestParam("isZip") Boolean isZip);

    @RequestMapping(value = "/put/shardPreparation", method = RequestMethod.POST)
    Result shardPreparation(@RequestParam("fileName") String fileName,
                            @RequestParam("shardNum") Integer shardNum,
                            @RequestParam("shardSize") Long shardSize,
                            @RequestParam("bucketId") String bucketId,
                            @RequestParam("isZip") Boolean isZip,
                            @RequestParam("originMd5") String originMd5);

    @RequestMapping(value = "/put/uploadShard",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<Integer> uploadShard(@RequestParam("no") Integer no,
                                @RequestParam("totalMd5") String totalMd5,
                                @RequestParam("ownMd5") String ownMd5,
                                @RequestPart("file") MultipartFile file,
                                @RequestParam("key") String key);

    @RequestMapping(value = "/put/checkShard", method = RequestMethod.POST)
    Result<Set<Integer>> checkShard(@RequestParam("md5") String md5,
                                    @RequestParam("key") String key);

    @RequestMapping(value = "/delete/delFile", method = RequestMethod.POST)
    Result delFile(@RequestParam String bucketId,
                   @RequestParam String fileName,
                   @RequestParam Boolean isForever);


    @RequestMapping(value = "/delete/recoverFile", method = RequestMethod.POST)
    Result recoverFile(@RequestParam String bucketId,
                       @RequestParam String fileName);

    @RequestMapping(value = "/get/getBucket/{bucketId}", method = RequestMethod.GET)
    Result<Bucket> getBucket(@PathVariable String bucketId);

    @RequestMapping(value = "/heartbeat/pong", method = RequestMethod.GET)
    Node pong();

    /**
     * 以下接口不对外开放
     */
    @RequestMapping(value = "/get/getBucketMsg", method = RequestMethod.POST)
    BucketMsg getBucketMsg(@RequestBody BucketMsg bucketMsg);

    @RequestMapping(value = "/delete/delBucket", method = RequestMethod.POST)
    void delBucket(@RequestParam String bucketId);
}
