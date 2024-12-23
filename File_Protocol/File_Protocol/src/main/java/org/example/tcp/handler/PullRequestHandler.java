package org.example.tcp.handler;

import org.example.domain.Message.AskRequestMessage;
import org.example.domain.Message.PullRequestMessage;
import org.example.property.FileProperty;
import org.example.utils.BucketUtil;
import org.example.utils.FileUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

//server端——数据恢复
@ChannelHandler.Sharable
@Component
public class PullRequestHandler extends SimpleChannelInboundHandler<PullRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PullRequestMessage msg) throws Exception {
        Map<String, Integer> nvMap = BucketUtil.getFileSetByName(msg.getBucketId());
        Set<String> keySet = nvMap.keySet();
        for (String filePath : keySet) {
            Integer version = nvMap.get(filePath);
            String from = filePath + "/" + version;
            String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
            String to = FileProperty.copyPath + msg.getBucketId()
                    + "/" + fileName + "/" + version;
            FileUtil.copyFile(from, to);//有创建目录功能
            AskRequestMessage message =
                    new AskRequestMessage(msg.getBucketId(), fileName, version);
            message.complete();
            ctx.channel().writeAndFlush(message);
        }
    }
}
