package org.example.tcp.autoCopy;

import org.example.domain.Message.AskRequestMessage;
import org.example.property.FileProperty;
import org.example.property.TCPProperty;
import org.example.utils.BucketUtil;
import org.example.utils.TCPUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AutoCopyTask {


    @Scheduled(cron = "0/24 * * * * ?")
    @Async
    public void autoCopy() {
        if (TCPProperty.autoCopy) {
            //获取此节点所有bucketId
            List<String> bucketIdList = new ArrayList<>();

            File file = new File(FileProperty.realPath);

            File[] tempList = file.listFiles();
            for (File value : tempList) {
                if (value.isDirectory()) {
                    bucketIdList.add(value.getName());
                }
            }

            for (String bucketId : bucketIdList) {
                Map<String, Integer> nvMap = BucketUtil.getFileSetByName(bucketId);
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
