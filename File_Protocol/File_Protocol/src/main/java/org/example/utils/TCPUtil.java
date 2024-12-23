package org.example.utils;


import org.example.domain.TCP.Client;
import org.example.domain.FileLock;
import org.example.domain.Message.AskRequestMessage;
import org.example.property.FileProperty;

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
