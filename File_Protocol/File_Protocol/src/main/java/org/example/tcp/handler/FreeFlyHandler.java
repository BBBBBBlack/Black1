package org.example.tcp.handler;

import org.example.domain.Message.Message;
import org.example.utils.FreeFly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
@ChannelHandler.Sharable
public class FreeFlyHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ByteBuf buf = FreeFly.bufMap.get(msg.getRandomStr());
        if (!(buf == null)) {
            System.out.println("2345");
            buf.release();
        }
    }
}
