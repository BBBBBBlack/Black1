package org.example.controller;


import io.netty.channel.Channel;
import org.example.domain.Message.AskRequestMessage;
import org.example.domain.Message.DefaultRegion;
import org.example.domain.Message.PullRequestMessage;
import org.example.domain.Result;
import org.example.domain.TCP.Client;
import org.example.domain.TCP.ClientFactory;
import org.example.domain.TCP.Server;
import org.example.property.TCPProperty;
import org.example.utils.BucketUtil;
import org.example.utils.DateUtil;
import org.example.utils.TCPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/tcp")
public class TCPController {

    @Autowired
    ClientFactory clientFactory;
    @Autowired
    Server server;

    @RequestMapping("/test")
    public void test() {
        Channel channel = Client.channelMap.get(TCPUtil.getMyIp() + ":" + 9002);
        try {
            FileChannel fileChannel =
                    new FileInputStream("C:\\Users\\black\\Desktop\\9ec7422b68214c3c99da12e4129c69ee.jpg").getChannel();
            channel.writeAndFlush(new DefaultRegion(fileChannel, 0, 100));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/server")
    public Result server() {
        if (server.createServer(TCPProperty.TCPPort)) {
            return new Result(200, "服务端开启");
        }
        return new Result(500, "服务端开启失败");
    }

    @RequestMapping("/client")
    public Result<String> client(@RequestParam String ip,
                                 @RequestParam Integer port) {
        if (clientFactory.createClient(ip, port)) {
            return new Result<>(200, "客户端开启", ip + ":" + port);
        }
        return new Result<>(500, "客户端开启失败");
    }

    @RequestMapping("/copy")
    public Result copy(@RequestParam String ip,
                       @RequestParam Integer port,
                       @RequestParam String bucketId) {
//        DateUtil.printDate();
        Map<String, Integer> nvMap = BucketUtil.getFileSetByName(bucketId);
        Set<String> keySet = nvMap.keySet();
        for (String filePath : keySet) {
            Integer version = nvMap.get(filePath);
            String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
            AskRequestMessage message = new AskRequestMessage(bucketId, fileName, version);
            if (!TCPUtil.autoCopy(ip, port, message)) {
                return new Result(400, "客户端未开启");
            }
        }
        return new Result(200, "开启备份");
    }


    @RequestMapping("/recover")
    public Result recover(@RequestParam String ip,
                          @RequestParam Integer port,
                          @RequestParam String bucketId) {
        if (Client.sendMessage(ip, port, new PullRequestMessage(bucketId))) {
            return new Result(200, "开启" + bucketId + "数据拉取");
        }
        return new Result(200, "客户端未开启");
    }


    @RequestMapping("/shutdownServer")
    public Result shutdownServer() {
        Server.shutdown(TCPProperty.TCPPort);
        return new Result(200, "关闭服务端");
    }

    @RequestMapping("/shutdownClient")
    public Result shutdownClient(@RequestParam String ip) {
        Client.shutdown(ip, TCPProperty.TCPPort);
        return new Result(200, "关闭客户端");
    }
}
