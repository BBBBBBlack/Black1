package com.example.test01_coll.utils;


import com.example.test01_coll.domain.entity.Client;
import com.example.test01_coll.domain.entity.FileLock;
import com.example.test01_coll.domain.entity.Message.AskRequestMessage;
import com.example.test01_coll.property.FileProperty;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TCPUtil {

    public static boolean autoCopy(String ip, Integer port, AskRequestMessage message) {
        String fileName = message.getFileName();
        String from;
        //单机备份,复制一份
        from = FileProperty.realPath + message.getBucketId()
                + "/" + fileName + "/" + message.getVersion();
        String to = FileProperty.copyPath + message.getBucketId()
                + "/" + fileName + "/" + message.getVersion();
        if (!FileLock.isLocked(from) && !FileLock.isLocked(to)) {
            System.out.println("进来了");
            FileUtil.copyFile(from, to);//有创建目录功能
            message.complete();
            return Client.sendMessage(ip, port, message);
        }
        return true;
    }

    public static String getMyIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

}
