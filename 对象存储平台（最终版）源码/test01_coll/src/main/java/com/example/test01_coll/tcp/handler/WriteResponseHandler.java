package com.example.test01_coll.tcp.handler;

import com.example.test01_coll.domain.entity.FileLock;
import com.example.test01_coll.domain.entity.Message.WriteResponseMessage;
import com.example.test01_coll.property.FileProperty;
import com.example.test01_coll.utils.DateUtil;
import com.example.test01_coll.utils.FileUtil;
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
        DateUtil.printDate();
    }
}
