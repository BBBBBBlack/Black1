package com.example.test01_coll.utils;

import com.backblaze.erasure.ReedSolomon;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ReedSolomonEncoder {
    public static final int DATA_SHARDS = 2;
    public static final int PARITY_SHARDS = 1;
    public static final int TOTAL_SHARDS = DATA_SHARDS + PARITY_SHARDS;
    public static final int BYTES_IN_INT = 4;

    private ReedSolomon reedSolomon;
    private byte[][] shards;

    public ReedSolomonEncoder() {
        this.reedSolomon = ReedSolomon.create(DATA_SHARDS, PARITY_SHARDS);
    }

    public void encodeFile(String inputFilePath, String outputFilePathPrefix) throws IOException {
        // Read input file
        File inputFile = new File(inputFilePath);
        long inputFileSize = inputFile.length();
        int shardSize = (int) Math.ceil((double) inputFileSize / DATA_SHARDS);
        this.shards = new byte[TOTAL_SHARDS][shardSize];
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            for (int i = 0; i < DATA_SHARDS; i++) {
                int bytesRead = inputStream.read(this.shards[i]);
                if (bytesRead < shardSize) {
                    // 填充不足的数据
                    for (int j = bytesRead; j < shardSize; j++) {
                        this.shards[i][j] = 0;
                    }
                }
            }
        }

        // 创建校验码
        this.reedSolomon.encodeParity(this.shards, 0, shardSize);

        // 写文件
        for (int i = 0; i < TOTAL_SHARDS; i++) {
            String outputFilePath = outputFilePathPrefix + "/" + i;
            File file = new File(outputFilePath);
            if (!file.exists()) {
                FileUtil.createFile(outputFilePathPrefix, i);
            }
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(intToBytes(i));
                outputStream.write(this.shards[i]);
            }
        }
    }

    private static byte[] intToBytes(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) i;
        return result;
    }
}
