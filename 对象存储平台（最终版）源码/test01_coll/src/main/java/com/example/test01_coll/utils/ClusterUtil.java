package com.example.test01_coll.utils;

import com.example.test01_coll.domain.entity.Node;
import com.example.test01_coll.property.ClusterProperty;

import java.util.Set;

public class ClusterUtil {
    public static String getIpFromSocket(String socket) {
        return socket.substring(0, socket.lastIndexOf(':'));
    }

    public static Integer getPortFromSocket(String socket) {
        return Integer.parseInt(socket.substring(socket.lastIndexOf(':') + 1));
    }

    public static String distributeSlot(String bucketId, String fileName) {
        String key = bucketId + ":" + fileName;
        int hashCode = Math.abs(key.hashCode()) % 1024;
        if (hashCode >= ClusterProperty.fromSlot && hashCode <= ClusterProperty.toSlot) {
            return "localhost";
        }
        Set<Node> nodes = ClusterProperty.SocketAliveMap.keySet();
        Integer toId = null;
        for (Node node : nodes) {
            if (hashCode >= node.getFromSlot() && hashCode <= node.getToSlot()) {
                toId = node.getId();
                break;
            }
        }
        return toId == null ? null : ClusterProperty.SocketMap.get(toId);
    }
}
