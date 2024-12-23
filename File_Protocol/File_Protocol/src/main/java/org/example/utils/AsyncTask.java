package org.example.utils;

import org.example.domain.FileLock;
import org.example.domain.Message.WriteRequestMessage;
import org.example.property.FileProperty;
import org.example.property.TCPProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class AsyncTask {

    @Async
    public void combineTask(WriteRequestMessage msg, String uri) {
        combine(msg);
        FileUtil.deleteDirectory(uri);
    }


    private void combine(WriteRequestMessage message) {
        String from = FileProperty.tempPath + message.getBucketId() + "/"
                + message.getFileName() + "/" + message.getVersion();
        String to = FileProperty.realPath + message.getBucketId()
                + "/" + message.getFileName() + "/" + message.getVersion();
        if (!FileLock.isLocked(to)) {
            try {
                FileUtil.createFile(to.substring(0, to.lastIndexOf('/')), message.getVersion());
                RandomAccessFile accessFile = new RandomAccessFile(to, "rw");
                AtomicInteger fileCount = new AtomicInteger();
                Files.walkFileTree(Paths.get(from), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        String path = from + "/" + file.getFileName();
                        File fromFile = new File(path);
                        FileInputStream fis = new FileInputStream(fromFile);
                        long offset = (Long.parseLong(String.valueOf(file.getFileName())) - 1)
                                * TCPProperty.maxSend;
                        accessFile.seek(offset);
                        byte[] bytes = new byte[TCPProperty.maxSend];
                        int len = 0;
                        while ((len = fis.read(bytes)) != -1) {
                            accessFile.write(bytes, 0, len);
                        }
                        fis.close();
                        if (fileCount.incrementAndGet() == message.getTotalNum()) {
                            accessFile.close();
                        }
                        return super.visitFile(file, attrs);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FileLock.addCopyFlag(from, 3);
        }
    }


}
