package org.example.domain.TCP;

import org.example.domain.Message.Message;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Client {
    public static Map<String, Channel> channelMap = new HashMap<>();

    public static boolean sendMessage(String ip, int port, Message message) {
//        if (message instanceof )
        Channel channel = channelMap.get(ip + ":" + port);
        if (channel != null) {
            channel.writeAndFlush(message);
            return true;
        }
        return false;
    }

    public static void shutdown(String ip, int port) {
        Channel channel = channelMap.get(ip + ":" + port);
        if (channel != null) {
            channel.close();
            channelMap.remove(ip + ":" + port);
        }
    }
}
