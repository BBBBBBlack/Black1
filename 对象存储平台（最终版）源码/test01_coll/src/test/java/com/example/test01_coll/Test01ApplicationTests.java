package com.example.test01_coll;

import com.example.test01_coll.cache.BucketCache;
import com.example.test01_coll.pojo.BucketMsg;
import com.example.test01_coll.utils.FileUtil;
import com.example.test01_coll.utils.ImageUtil;
import com.example.test01_coll.utils.TCPUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class Test01ApplicationTests {
    @Autowired
    BucketCache bucketCache;
    @Autowired
    RedisTemplate<Object, Object> template;
    @Autowired
    FileUtil fileUtil;

    @Test
    void contextLoads() throws IOException {
//        Thumbnails.of("D:\\DataBase\\test01\\realPath\\1\\9ec7422b68214c3c99da12e4129c69ee.jpg\\2")
//                .scale(0.25f)
//                .outputQuality(0.25f)
//                .toFile("C:\\Users\\black\\Desktop\\1.jpg");
//        D:\DataBase\test01\realPath\1\9ec7422b68214c3c99da12e4129c69ee.jpg\2
        File file = new File("D:\\DataBase\\test01\\realPath\\1\\1234321.pdf\\1");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        byte[] bytes = new byte[(int) channel.size()];
        channel.read(ByteBuffer.wrap(bytes));
        System.out.println(ImageUtil.isImage(bytes));
    }

    //2293
    //1526
    public static void main(String[] args) throws IOException {
        //85735890144214589605142203574359595775
        String md5 = FileUtil.getMd5("C:\\Users\\black\\Desktop\\9ec7422b68214c3c99da12e4129c69ee.jpg");
        System.out.println(md5);
//        System.out.println(TCPUtil.getMyIp());
    }

}
