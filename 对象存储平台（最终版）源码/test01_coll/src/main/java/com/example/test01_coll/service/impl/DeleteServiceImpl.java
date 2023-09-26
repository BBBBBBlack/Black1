package com.example.test01_coll.service.impl;

import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.property.FileProperty;
import com.example.test01_coll.service.DeleteService;
import com.example.test01_coll.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteServiceImpl implements DeleteService {

    @Autowired
    BucketCache bucketCache;

    @Override
    public Result delFile(String bucketId, String fileName, Boolean isForever) {
        if (isForever) {
            FileUtil.deleteDirectory(FileProperty.realPath + bucketId + "/" + fileName);
            FileUtil.deleteDirectory(FileProperty.delPath + bucketId + "/" + fileName);
        } else {
            FileUtil.moveDirectory(FileProperty.realPath + bucketId + "/" + fileName,
                    FileProperty.delPath + bucketId + "/" + fileName);
        }
        bucketCache.getFileSetByName(bucketId, 3);
        return new Result(200, "已删除");
    }

    @Override
    public Result recoverFile(String bucketId, String fileName) {
        if (FileUtil.moveDirectory(FileProperty.delPath + bucketId + "/" + fileName,
                FileProperty.realPath + bucketId + "/" + fileName)) {
            return new Result(200, "已恢复");
        }
        return new Result(200, "恢复失败");
    }
}
