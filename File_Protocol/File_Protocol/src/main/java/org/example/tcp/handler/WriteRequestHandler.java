package org.example.tcp.handler;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.domain.Message.WriteRequestMessage;
import org.example.domain.Message.WriteResponseMessage;
import org.example.property.FileProperty;
import org.example.utils.AsyncTask;
import org.example.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
@Component
public class WriteRequestHandler extends SimpleChannelInboundHandler<WriteRequestMessage> {

    @Autowired
    AsyncTask asyncTask;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WriteRequestMessage msg) throws Exception {
        String uri = FileProperty.tempPath + msg.getBucketId()
                + "/" + msg.getFileName() + "/" + msg.getVersion();
        String path = uri + "/" + msg.getSequenceId();

        FileUtil.createFile(uri, msg.getSequenceId());
        FileChannel fileChannel = FileChannel.open(Paths.get(path),
                StandardOpenOption.READ, StandardOpenOption.WRITE);
        MappedByteBuffer buffer = fileChannel
                .map(FileChannel.MapMode.READ_WRITE, 0, msg.getContent().length);
//        buffer.flip();
        buffer.put(msg.getContent());
        fileChannel.close();
//        Cleaner cleaner = ((sun.nio.ch.DirectBuffer) buffer).cleaner();
//        if (cleaner != null) {
//            cleaner.clean();
//        }
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get(uri), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        if (fileCount.get() == msg.getTotalNum()) {
            asyncTask.combineTask(msg, uri);
            ctx.channel().writeAndFlush
                    (new WriteResponseMessage(msg.getBucketId(), msg.getFileName(), msg.getVersion()));
        }
        System.out.println(Thread.currentThread().getName() + "  received:" +
                msg.getFileName() + "---" + msg.getSequenceId());
    }


}
