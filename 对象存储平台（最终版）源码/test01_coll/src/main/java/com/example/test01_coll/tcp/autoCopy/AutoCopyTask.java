package com.example.test01_coll.tcp.autoCopy;

import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.domain.entity.Message.AskRequestMessage;
import com.example.test01_coll.property.FileProperty;
import com.example.test01_coll.property.TCPProperty;
import com.example.test01_coll.utils.TCPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AutoCopyTask {
    @Autowired
    BucketCache bucketCache;

    @Scheduled(cron = "0/24 * * * * ?")
    @Async
    public void autoCopy() {
        if (TCPProperty.autoCopy) {
            //获取此节点所有bucketId
            List<String> bucketIdList = new ArrayList<>();

            File file=new File(FileProperty.realPath);

            File[] tempList = file.listFiles();
            for (File value : tempList) {
                if (value.isDirectory()) {
                    bucketIdList.add(value.getName());
                }
            }

            for (String bucketId : bucketIdList) {
                Map<String, Integer> nvMap = bucketCache.getFileSetByName(bucketId, 1);
                Set<String> keySet = nvMap.keySet();
                for (String filePath : keySet) {
                    Integer version = nvMap.get(filePath);
                    String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
                    AskRequestMessage message = new AskRequestMessage(bucketId, fileName, version);
                    TCPUtil.autoCopy(TCPProperty.copyIp, TCPProperty.copyPort, message);
                }
            }
        }
    }
}
