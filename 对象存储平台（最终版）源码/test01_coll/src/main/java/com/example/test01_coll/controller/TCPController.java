package com.example.test01_coll.controller;


import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.cache.Md5Cache;
import com.example.test01_coll.domain.entity.Client;
import com.example.test01_coll.domain.entity.Factory.ClientFactory;
import com.example.test01_coll.domain.entity.Message.AskRequestMessage;
import com.example.test01_coll.domain.entity.Message.DefaultRegion;
import com.example.test01_coll.domain.entity.Message.PullRequestMessage;
import com.example.test01_coll.domain.entity.Result;
import com.example.test01_coll.domain.entity.Server;
import com.example.test01_coll.property.TCPProperty;
import com.example.test01_coll.utils.DateUtil;
import com.example.test01_coll.utils.TCPUtil;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.Set;

//10:35--12:05
//27:18--28:43
//48:07--49:33
//56:11--58:03
@RestController
@RequestMapping("/tcp")
public class TCPController {

    @Autowired
    BucketCache bucketCache;
    @Autowired
    Md5Cache md5Cache;
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
//        Client.sendMessage("192.168.31.75", 9002,
//                new PullColdRequestMessage("1", "9ec7422b68214c3c99da12e4129c69ee.jpg"));
    }

    @RequestMapping("/server")
    public Result server() {
        if (server.createServer(TCPProperty.TCPPort)) {
            return new Result(200, "服务端开启");
        }
        return new Result(500, "服务端开启失败");
    }

    @RequestMapping("/client")
    public Result<String> client(@RequestParam String ip) {
        if (clientFactory.createClient(ip, TCPProperty.TCPPort)) {
            return new Result<>(200, "客户端开启", ip + ":" + TCPProperty.TCPPort);
        }
        return new Result<>(500, "客户端开启失败");

    }

    @RequestMapping("/copy")
    public Result copy(@RequestParam String ip,
                       @RequestParam String bucketId) {
        DateUtil.printDate();
        Map<String, Integer> nvMap = bucketCache.getFileSetByName(bucketId, 1);
        Set<String> keySet = nvMap.keySet();
        for (String filePath : keySet) {
            Integer version = nvMap.get(filePath);
            String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
            AskRequestMessage message = new AskRequestMessage(bucketId, fileName, version);
            if (!TCPUtil.autoCopy(ip, TCPProperty.TCPPort, message)) {
                return new Result(400, "客户端未开启");
            }
//            if (!TCPUtil.autoCopy(ip, 9002, message)) {
//                return new Result(400, "客户端未开启");
//            }
//            client.sendMessage(new AskRequestMessage("bucket1", fileName));
        }
        return new Result(200, "开启备份");
    }

    @RequestMapping("/recover")
    public Result recover(@RequestParam String ip,
                          @RequestParam String bucketId) {
        if (Client.sendMessage(ip, TCPProperty.TCPPort, new PullRequestMessage(bucketId))) {
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
