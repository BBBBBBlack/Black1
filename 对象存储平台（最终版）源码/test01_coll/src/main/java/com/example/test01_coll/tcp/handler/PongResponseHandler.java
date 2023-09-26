package com.example.test01_coll.tcp.handler;

import com.example.test01_coll.domain.entity.Message.PONGMessage;
import com.example.test01_coll.property.ClusterProperty;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.util.Date;
@ChannelHandler.Sharable
@Component
public class PongResponseHandler extends SimpleChannelInboundHandler<PONGMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PONGMessage msg) throws Exception {
        ClusterProperty.SocketAliveMap
                .put(msg.getNode(), new Date().getTime());
//        System.out.println(ClusterProperty.SocketAliveMap);
    }
}
