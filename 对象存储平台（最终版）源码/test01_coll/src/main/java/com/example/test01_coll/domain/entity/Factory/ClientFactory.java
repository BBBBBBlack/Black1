package com.example.test01_coll.domain.entity.Factory;

import com.example.test01_coll.domain.entity.Client;
import com.example.test01_coll.tcp.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientFactory {
    @Autowired
    AskRequestHandler askRequestHandler;
    @Autowired
    AskResponseHandler askResponseHandler;
    @Autowired
    MessageCodec messageCodec;
    @Autowired
    PingRequestHandler pingRequestHandler;
    @Autowired
    PongResponseHandler pongResponseHandler;
    @Autowired
    PullColdRequestHandler pullColdRequestHandler;
    @Autowired
    WriteRequestHandler writeRequestHandler;
    @Autowired
    WriteResponseHandler writeResponseHandler;

    public boolean createClient(String ip, Integer port) {
        String key = ip + ":" + port;
        Channel channel1 = Client.channelMap.get(key);
        if (channel1 == null || !channel1.isOpen()) {
            EventLoopGroup group = new NioEventLoopGroup(2);
            Channel channel = null;
            try {
                channel = new Bootstrap()
                        .group(group)
                        .handler(new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline()
                                        .addLast(new LengthFieldBasedFrameDecoder(1024 * 1024 * 1024, 22, 4, 0, 0))
                                        .addLast(messageCodec)
                                        .addLast(pongResponseHandler)
                                        .addLast(askResponseHandler)
                                        .addLast(writeResponseHandler)
                                        //拉取
                                        .addLast(askRequestHandler)
                                        .addLast(writeRequestHandler);
                            }
                        })
                        .channel(NioSocketChannel.class).connect(ip, port)
                        .sync()
                        .channel();
            } catch (InterruptedException e) {
                e.printStackTrace();
                channel.close();
                return false;
            }
            ChannelFuture closeFuture = channel.closeFuture();
            closeFuture.addListener((ChannelFutureListener) channelFuture -> {
                group.shutdownGracefully();
            });
            Client.channelMap.put(ip + ":" + port, channel);
        }
        return true;
    }
}
