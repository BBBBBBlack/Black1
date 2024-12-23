package org.example.tcp.handler;

import org.example.domain.FileLock;
import org.example.domain.Message.WriteResponseMessage;
import org.example.property.FileProperty;
import org.example.utils.DateUtil;
import org.example.utils.FileUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class WriteResponseHandler extends SimpleChannelInboundHandler<WriteResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WriteResponseMessage msg) throws Exception {
        ctx.channel().flush();
        String path = FileProperty.copyPath + msg.getBucketId()
                + "/" + msg.getFileName() + "/" + msg.getVersion();
        FileUtil.deleteFile(path);
        FileLock.unlock(path);
//        DateUtil.printDate();
    }
}
