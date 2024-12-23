package org.example.property;

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

    @Value("${path-root}")
    public void setPath(String rootPath) {
        FileProperty.realPath = rootPath + "realPath/";
        FileProperty.tempPath = rootPath + "tempPath/";
        FileProperty.copyPath = rootPath + "copyPath/";
        FileProperty.delPath = rootPath + "delPath/";
    }

}
