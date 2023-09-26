package com.example.test01_coll.controller;

import com.example.test01_coll.domain.entity.Bucket;
import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.pojo.BucketMsg;
import com.example.test01_coll.service.ClusterGetService;
import com.example.test01_coll.service.GetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/get")
public class GetController {

    @Autowired
    private GetService getService;

    @Autowired
    private ClusterGetService cGetService;

    @GetMapping("/getBucket/{bucketId}")
    public Result<Bucket> getBucket(@PathVariable String bucketId,
                                    @RequestHeader(value = "from", required = false) String from) {
        if (from != null || !ClusterProperty.cluster) {
            return getService.getBucket(bucketId);
        } else {
            return cGetService.getBucket(bucketId);
        }
    }

    @GetMapping("/getFile")
    public void getFile(@RequestParam String bucketId,
                        @RequestParam String fileName,
                        @RequestParam(required = false) Integer version,
                        @RequestHeader(value = "from", required = false) String from,
                        HttpServletResponse response) {
        if (from != null || !ClusterProperty.cluster) {
            getService.getFile(bucketId, fileName, version, response);
        } else {
            cGetService.getFile(bucketId, fileName, version, response);
        }
    }

    @GetMapping("/coldGet")
    public void coldGet(@RequestParam String bucketId,
                        @RequestParam String fileName) {
        cGetService.coldGet(bucketId, fileName);
    }

    @PostMapping("/getBucketMsg")
    public BucketMsg getBucketMsg(@RequestBody BucketMsg bucketMsg) {
        return cGetService.getBucketMsg(bucketMsg);
    }
}
