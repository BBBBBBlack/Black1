package org.example.utils;

import org.example.domain.FileLock;
import org.example.property.TCPProperty;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FileUtil {

    public static String doGetMd5(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        BufferedInputStream stream = new BufferedInputStream(inputStream);
        byte[] buffer = new byte[8192];
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        int len = 0;
        while ((len = stream.read(buffer)) != -1) {
            md5.update(buffer, 0, len);
        }
        byte[] bytes = md5.digest();
        BigInteger bigInteger = new BigInteger(1, bytes);
        stream.close();
        return bigInteger.toString();
    }


    public static String getMd5(String path) {
        String md5 = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                md5 = doGetMd5(inputStream);
            } else {
                throw new FileNotFoundException();
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    private static boolean doCopyFile(String from, String to) {
        FileChannel input = null;
        FileChannel output = null;
        try {
            input = new FileInputStream(from).getChannel();
            output = new FileOutputStream(to).getChannel();
            long size = input.size();
            int pos = 0;
            while (pos < size - 1) {
                long max = (size - pos - 1) > TCPProperty.maxSend ? TCPProperty.maxSend : (size - pos - 1);
                input.transferTo(pos, max, output);
                pos += max;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean copyFile(String from, String to) {
        FileLock.lock(from);
        boolean flag = true;
        File path = new File(to.substring(0, to.lastIndexOf('/')));
        if (!path.isDirectory()) {
            path.mkdirs();
            path.setWritable(true, false);
            path.setReadable(true, false);
            path.setExecutable(true, false);
        }
        File toFile = new File(to);
        if (!toFile.exists()) {
            try {
                toFile.createNewFile();
                toFile.setWritable(true, false);
                toFile.setReadable(true, false);
                toFile.setExecutable(true, false);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            flag = !getMd5(to).equals(getMd5(from));
        }
        if (flag) {
            doCopyFile(from, to);
        }
        FileLock.unlock(from);
        return true;
    }

    public static Integer deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return -1;
        }
        return file.delete() ? 1 : 0;
    }


    public static void deleteDirectory(String uri) {
        try {
            Path path = Paths.get(uri);
            if (Files.isDirectory(path)) {
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                            // 先去遍历删除文件
                            @Override
                            public FileVisitResult visitFile(Path file,
                                                             BasicFileAttributes attrs) throws IOException {
                                Files.delete(file);
                                return FileVisitResult.CONTINUE;
                            }
                        }
                );
                Files.delete(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean createFile(String dir, Integer version) {
        File dirPath = new File(dir);
        File filePath = new File(dir + "/" + version);
        if (!dirPath.isDirectory()) {
            dirPath.mkdirs();
            dirPath.setWritable(true, false);
            dirPath.setReadable(true, false);
            dirPath.setExecutable(true, false);
        }
        if (!filePath.isFile()) {
            try {
                filePath.createNewFile();
                filePath.setWritable(true, false);
                filePath.setReadable(true, false);
                filePath.setExecutable(true, false);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


}