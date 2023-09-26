package com.example.test01_coll.utils;

import com.backblaze.erasure.ReedSolomon;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Component
public class ReedSolomonDecoder {

    private static final int DATA_SHARDS = 2;
    private static final int PARITY_SHARDS = 1;
    private static final int TOTAL_SHARDS = DATA_SHARDS + PARITY_SHARDS;
    private static final int BYTES_IN_INT = 4;

    private ReedSolomon reedSolomon;
    private byte[][] shards;

    public ReedSolomonDecoder() {
        this.reedSolomon = ReedSolomon.create(DATA_SHARDS, PARITY_SHARDS);
    }

    public void decodeFile(String[] inputFilePaths, String outputFilePath) throws IOException {
        
        // 读入文件
        this.shards = new byte[TOTAL_SHARDS][];
        int shardSize = -1;
        boolean[] shardPresent = new boolean[TOTAL_SHARDS];
        int availableShards = 0;
        for (int i = 0; i < TOTAL_SHARDS; i++) {
            String inputFilePath = inputFilePaths[i];
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                // 哪部分文件丢失
                shardPresent[i] = false;
                continue;
            }
            byte[] shardBytes = readAllBytes(inputFilePath);
            int shardIndex = bytesToInt(Arrays.copyOfRange(shardBytes, 0, BYTES_IN_INT));
            this.shards[shardIndex] = Arrays.copyOfRange(shardBytes, BYTES_IN_INT, shardBytes.length);
            if (shardSize == -1) {
                shardSize = this.shards[shardIndex].length;
            }
            shardPresent[i] = true;
            availableShards++;
        }
        for (int i = 0; i < TOTAL_SHARDS; i++) {
            if (this.shards[i] == null) {
                this.shards[i] = new byte[shardSize];
            }
        }

        System.out.println(Arrays.toString(shardPresent));

        if (availableShards < DATA_SHARDS) {
            throw new RuntimeException("Not enough data shards available to recover the file");
        }

        // 解码
        this.reedSolomon.decodeMissing(this.shards, shardPresent, 0, shardSize);

        //输出文件
        try (FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
            for (int i = 0; i < DATA_SHARDS; i++) {
                outputStream.write(this.shards[i]);
            }
        }
    }


    public static byte[] readAllBytes(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return new byte[]{0, 0, 0, 0};
        }
        return Files.readAllBytes(path);
    }


    private static int bytesToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }
}

