package org.example.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class TCPProperty implements Serializable {
    public static String magicNum = "G.E.M.";
    public static String serializable = "json";
    public static Integer maxSend = 262144;
    public static int TCPPort;

    @Value("${tcp.port}")
    public void setTCPPort(int TCPPort) {
        TCPProperty.TCPPort = TCPPort;
    }


    public static Boolean autoCopy;
    public static String copyIp;
    public static Integer copyPort;
}
