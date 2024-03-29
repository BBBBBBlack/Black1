package com.example.test01_coll.tcp.handler;

import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.domain.entity.Message.AskRequestMessage;
import com.example.test01_coll.domain.entity.Message.PullColdRequestMessage;
import com.example.test01_coll.property.FileProperty;
import com.example.test01_coll.utils.FileUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@ChannelHandler.Sharable
@Component
public class PullColdRequestHandler extends SimpleChannelInboundHandler<PullColdRequestMessage> {
    @Autowired
    BucketCache bucketCache;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PullColdRequestMessage msg) throws Exception {
        Map<String, Integer> nvMap = bucketCache.getColdFileSetByName(msg.getBucketId(), false);
        if (nvMap != null && !nvMap.isEmpty()) {
            String key = FileProperty.realPath + msg.getBucketId() + "/" + msg.getFileName() + "-cold";
            Integer version = nvMap.get(key);
            if (version != null) {
                File file = new File(key + "/" + version);
                String[] list = file.list();
                if (list != null) {
                    String from = key + "/" + version + "/" + list[0];
                    String to = FileProperty.copyPath + msg.getBucketId()
                            + "/" + msg.getFileName() + "-cold/" + version + "/" + list[0];
                    FileUtil.copyFile(from, to);//有创建目录功能
                    AskRequestMessage message =
                            new AskRequestMessage(msg.getBucketId(),
                                    msg.getFileName() + "-cold/" + version,
                                    Integer.parseInt(list[0]));
                    message.complete();
                    ctx.channel().writeAndFlush(message);
                }
            }
        }
    }
}
