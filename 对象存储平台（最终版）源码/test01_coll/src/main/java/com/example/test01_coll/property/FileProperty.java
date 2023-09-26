package com.example.test01_coll.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileProperty {
    public static Integer apiPort;//接口开放端口
    public static String realPath;
    public static String tempPath;
    public static String copyPath;
    public static String delPath;

    @Value("${server.port}")
    public void setApiPort(Integer apiPort) {
        FileProperty.apiPort = apiPort;
    }
}
