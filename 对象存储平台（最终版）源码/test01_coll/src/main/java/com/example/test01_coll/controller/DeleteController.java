package com.example.test01_coll.controller;

import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.service.ClusterDeleteService;
import com.example.test01_coll.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delete")
public class DeleteController {
    @Autowired
    private DeleteService deleteService;

    @Autowired
    private ClusterDeleteService cDeleteService;

    @PostMapping("/delFile")
    public Result delFile(@RequestParam String bucketId,
                          @RequestParam String fileName,
                          @RequestParam Boolean isForever,
                          @RequestHeader(value = "from", required = false) String from) {
        if (from != null || !ClusterProperty.cluster) {
            return deleteService.delFile(bucketId, fileName, isForever);
        } else {
            return cDeleteService.delFile(bucketId, fileName, isForever);
        }
    }

    @PostMapping("/recoverFile")
    public Result recoverFile(@RequestParam String bucketId,
                              @RequestParam String fileName,
                              @RequestHeader(value = "from", required = false) String from) {
        if (from != null || !ClusterProperty.cluster) {
            return deleteService.recoverFile(bucketId, fileName);
        } else {
            return cDeleteService.recoverFile(bucketId, fileName);
        }
    }

    @PostMapping("/delBucket")
    public void delBucket(@RequestParam String bucketId) {
        cDeleteService.delBucket(bucketId);
    }
}
