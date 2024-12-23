package org.example.utils;

import org.example.property.FileProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

@Component
public class BucketUtil {


    //查看某一桶下所有文件

    public static Map<String, Integer> getFileSetByName(String bucketId) {
        return fileVersionScan(bucketId);
    }


    public static Map<String, Integer> fileVersionScan(String bucketId) {
        String uri = FileProperty.realPath + bucketId + "/";
        Path path = Paths.get(uri);
        Map<String, Integer> nvMap = new HashMap<>();//name-versionMap
        if (Files.isDirectory(path)) {
            try {
                //fileName层
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                            @Override
                            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                                if (!dir.equals(path) && dir.getParent().equals(path)) {
                                    //version层（文件）
                                    Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                                        @Override
                                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                                            if (file.getParent().equals(dir)) {
                                                String fileName = String.valueOf(file.getFileName());
                                                int newVersion = Integer.parseInt(fileName);
                                                nvMap.merge(dir.toString().replace('\\', '/'), newVersion, (a, b) -> b > a ? b : a);
                                                return super.visitFile(file, attrs);
                                            }
                                            return FileVisitResult.TERMINATE;
                                        }
                                    });
                                }
                                return super.preVisitDirectory(dir, attrs);
                            }
                        }
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nvMap;
    }


}
