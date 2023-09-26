package com.example.test01_coll.property;

import com.example.test01_coll.domain.entity.Node;

import java.util.Map;
import java.util.Set;

public class ClusterProperty {
    public static boolean cluster;
    public static int id;
    public static int fromSlot;
    public static int toSlot;
    public static Map<Integer, String> SocketMap;//<id,ip:port>
    public static Map<Node, Long> SocketAliveMap;//<node:port,time>
    public static Set<Node> allNodeSet;//<slot,ip:port>

}
