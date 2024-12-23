package org.example.tcp.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.domain.FileLock;
import org.example.domain.Message.AskResponseMessage;
import org.example.domain.Message.WriteRequestMessage;
import org.example.property.FileProperty;
import org.example.property.TCPProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Set;

@ChannelHandler.Sharable
@Component
public class AskResponseHandler extends SimpleChannelInboundHandler<AskResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AskResponseMessage msg) throws Exception {
        Set<Integer> fileSet = msg.getFileSet();
        if (fileSet != null) {
            String fileUri = FileProperty.copyPath + msg.getBucketId()
                    + "/" + msg.getFileName() + "/" + msg.getVersion();
            //锁住
            FileLock.lock(fileUri);
            File file = new File(fileUri);
            FileInputStream fis = new FileInputStream(file);
            long length = file.length();
            long num = (long) Math.ceil((double) length / TCPProperty.maxSend);
            int last = (int) (length - (num - 1) * TCPProperty.maxSend);
            MappedByteBuffer buffer = fis.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, length);
            byte[] bytes = new byte[TCPProperty.maxSend];
            for (int i = 1; i <= num; i++) {
                if (!fileSet.contains(i)) {
                    WriteRequestMessage message =
                            new WriteRequestMessage(msg.getBucketId(), msg.getFileName(), num, i, msg.getVersion());
                    if (i == num) {
                        byte[] bytes2 = new byte[last];
                        buffer.get(bytes2, 0, last);
                        message.setContent(bytes2);
                    } else {
                        buffer.get(bytes, 0, TCPProperty.maxSend);
//                    int read = fis.read(bytes);
                        message.setContent(bytes);
                    }

                    ctx.channel().writeAndFlush(message);
                    System.out.println(Thread.currentThread().getName() + "  sent:" +
                            message.getFileName() + "---" + message.getSequenceId());
                }
            }
            fis.close();
//            Cleaner cleaner = ((sun.nio.ch.DirectBuffer) buffer).cleaner();
//            if (cleaner != null) {
//                cleaner.clean();
//            }
        }
    }
}