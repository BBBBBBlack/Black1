package com.example.test01_coll.service.impl;

import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.cache.FileCache;
import com.example.test01_coll.domain.entity.Bucket;
import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.property.FileProperty;
import com.example.test01_coll.service.GetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class GetServiceImpl implements GetService {
    @Autowired
    private BucketCache bucketCache;
    @Autowired
    private FileCache fileCache;

    @Override
    public Result<Bucket> getBucket(String bucketId) {
        Bucket bucket = new Bucket(bucketId);
        Map<String, Integer> nvMap = bucketCache.getFileSetByName(bucketId, 1);
        Set<String> keySet = nvMap.keySet();
        Set<String> fileSet = bucket.getFileSet();
        if (fileSet != null) {
            fileSet.clear();
        } else {
            fileSet = new HashSet<>();
        }
        for (String path : keySet) {
            fileSet.add(path.substring(path.lastIndexOf('/') + 1));
        }
        bucket.setFileSet(fileSet);
        return new Result<>(200, "查询桶信息", bucket);
    }

    //167
    @Override
    public void getFile(String bucketId, String fileName, Integer version, HttpServletResponse response) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            response.reset();
            response.addHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            if (version == null) {
                String base64 = fileCache.getFile(bucketId, fileName, response, 1);
                if (base64 != null) {
                    byte[] bytes = Base64.getDecoder().decode(base64);
//                response.getOutputStream().write(bytes, 0, bytes.length);
//                response.getOutputStream().flush();
//                response.getOutputStream().close();
                    response.setContentLength(bytes.length);
                    bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
                    bufferedOutputStream.write(bytes, 0, bytes.length);
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
            } else {
                Map<String, Integer> nvMap = bucketCache.getFileSetByName(bucketId, 1);
                Integer latestVersion = nvMap.get(FileProperty.realPath + bucketId + "/" + fileName);
                int cnt = version;
                while (latestVersion + cnt > 0) {
                    int curVersion = latestVersion + cnt;
                    String path = FileProperty.realPath + bucketId + "/" + fileName + "/" + curVersion;
                    //写入response
                    if (Files.exists(Paths.get(path))) {
                        bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
                        int len;
                        while ((len = bis.read()) != -1) {
                            bufferedOutputStream.write(len);
                        }
                        break;
                    }
                    cnt--;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}