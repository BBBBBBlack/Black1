package com.example.test01_coll.service.impl;

import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.domain.entity.Bucket;
import com.example.test01_coll.domain.entity.Client;
import com.example.test01_coll.domain.entity.Factory.FeignClientFactory;
import com.example.test01_coll.domain.entity.Message.PullColdRequestMessage;
import com.example.test01_coll.domain.entity.Node;
import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.feign.TestFeign;
import com.example.test01_coll.pojo.BucketMsg;
import com.example.test01_coll.service.ClusterGetService;
import com.example.test01_coll.service.GetService;
import com.example.test01_coll.utils.ClusterUtil;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Service
public class ClusterGetServiceImpl implements ClusterGetService {

    @Autowired
    FeignClientFactory clientFactory;

    @Autowired
    GetService getService;
    @Autowired
    BucketCache bucketCache;

    @Override
    public void getFile(String bucketId, String fileName, Integer version, HttpServletResponse response) {
        String toSocket = ClusterUtil.distributeSlot(bucketId, fileName);
        if ("localhost".equals(toSocket)) {
            getService.getFile(bucketId, fileName, version, response);
        } else if (toSocket != null) {
            Response response1 = clientFactory
                    .getFeignClient(TestFeign.class, "http://" + toSocket)
                    .getFile(bucketId, fileName, version);
            try {
                InputStream inputStream = response1.body().asInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                response.setHeader("Content-Disposition", response1.headers().get("Content-Disposition").toString().replace("[", "").replace("]", ""));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
                int length = 0;
                byte[] temp = new byte[1024 * 10];
                while ((length = bufferedInputStream.read(temp)) != -1) {
                    bufferedOutputStream.write(temp, 0, length);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                bufferedInputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(111);
        }
    }

    @Override
    public Result<Bucket> getBucket(String bucketId) {
        Bucket bucket = getService.getBucket(bucketId).getData();
        Set<String> fileSet = bucket.getFileSet();
        Set<String> set = new HashSet<>(new HashSet<>(fileSet));
        Set<Node> nodes = ClusterProperty.SocketAliveMap.keySet();
        for (Node node : nodes) {
            String socket = ClusterProperty.SocketMap.get(node.getId());
            Result<Bucket> result1 = clientFactory
                    .getFeignClient(TestFeign.class, "http://" + socket)
                    .getBucket(bucketId);
            set.addAll(new HashSet<>(result1.getData().getFileSet()));
        }
        bucket.setFileSet(set);
        return new Result<>(200, "查询桶信息", bucket);
    }

    @Override
    public void coldGet(String bucketId, String fileName) {
        PullColdRequestMessage message = new PullColdRequestMessage(bucketId, fileName);
        Set<Node> nodes = ClusterProperty.SocketAliveMap.keySet();
        for (Node node : nodes) {
            Client.sendMessage(node.getIp(), node.getTCPPort(), message);
        }
//        int cnt = 0;
//        while (cnt < 5) {
//
//        }
    }

    @Override
    public BucketMsg getBucketMsg(BucketMsg bucketMsg) {
        return bucketCache.bucketMsgScan(bucketMsg);
    }
}
