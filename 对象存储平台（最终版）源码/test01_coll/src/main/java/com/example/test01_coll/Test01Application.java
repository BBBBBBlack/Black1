package com.example.test01_coll;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableAsync
@EnableFeignClients
@MapperScan("com.example.test01_coll.mapper")
public class Test01Application {

    // TODO 版本控制接口
    // TODO 单机自动备份
    // TODO 集群删除文件？
    // TODO 高并发
    public static void main(String[] args) {
        SpringApplication.run(Test01Application.class, args);
    }

}
