package com.example.test01_coll.tcp.handler;

import com.example.test01_coll.domain.entity.Message.PINGMessage;
import com.example.test01_coll.domain.entity.Message.PONGMessage;
import com.example.test01_coll.domain.entity.Node;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.property.FileProperty;
import com.example.test01_coll.property.TCPProperty;
import com.example.test01_coll.utils.TCPUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class PingRequestHandler extends SimpleChannelInboundHandler<PINGMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PINGMessage msg) throws Exception {
        Node node =
                new Node(ClusterProperty.id, TCPUtil.getMyIp(), FileProperty.apiPort, TCPProperty.TCPPort, ClusterProperty.fromSlot, ClusterProperty.toSlot,null);
        PONGMessage message = new PONGMessage(node);
        ctx.writeAndFlush(message);
    }
}
