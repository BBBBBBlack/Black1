package com.example.test01_coll.property;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class TCPProperty implements Serializable {
    public static String magicNum = "G.E.M.";
    public static String serializable = "json";
    public static Integer maxSend = 262144;
    public static int TCPPort = 9001;

    public static Boolean autoCopy;
    public static String copyIp;
    public static Integer copyPort;
}
