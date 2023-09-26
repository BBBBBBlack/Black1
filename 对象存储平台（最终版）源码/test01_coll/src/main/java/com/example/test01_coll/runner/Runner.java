package com.example.test01_coll.runner;

import com.example.test01_coll.domain.entity.Server;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.property.FileProperty;
import com.example.test01_coll.property.TCPProperty;
import com.example.test01_coll.utils.FileUtil;
import org.ini4j.Profile;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class Runner implements CommandLineRunner {
    @Autowired
    Server server;

    @Override
    public void run(String... args) throws Exception {
        Resource resource = new ClassPathResource("config/GEMLoadConfig.ini");
        Wini ini = null;
        try {
            ini = new Wini(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取路径配置
        Profile.Section path = ini.get("path");
        FileProperty.realPath = path.get("realPath");
        FileProperty.tempPath = path.get("tempPath");
        FileProperty.copyPath = path.get("copyPath");
        FileProperty.delPath = path.get("delPath");
        FileUtil.createDirectory(FileProperty.realPath);
        FileUtil.createDirectory(FileProperty.tempPath);
        FileUtil.createDirectory(FileProperty.copyPath);
        FileUtil.createDirectory(FileProperty.delPath);

        // TODO 获取自动备份配置
        Profile.Section autoCopy = ini.get("copy");
        TCPProperty.autoCopy = autoCopy.get("auto-copy", Boolean.class);
        if (TCPProperty.autoCopy) {
            TCPProperty.copyIp = autoCopy.get("copy-ip");
            TCPProperty.copyPort = autoCopy.get("copy-port", Integer.class);
        }
        //获取集群配置
        Profile.Section cluster = ini.get("cluster");
        ClusterProperty.cluster = cluster.get("cluster-enable", Boolean.class);
        if (ClusterProperty.cluster) {
            ClusterProperty.id = cluster.get("id", Integer.class);
            ClusterProperty.SocketMap = new HashMap<>();
            ClusterProperty.SocketAliveMap = new HashMap<>();
            //获取节点配置
            int i = 1;
            while (true) {
                Profile.Section section = ini.get("cluster-" + i);
                if (section != null) {
                    Integer id = section.get("id", Integer.class);
                    String socket = section.get("socket");
                    ClusterProperty.SocketMap.put(id, socket);
                } else {
                    break;
                }
                i++;
            }
            //分配slot
            int size = (int) Math.ceil((double) 1024 / i);
            ClusterProperty.fromSlot = size * (ClusterProperty.id - 1);
            ClusterProperty.toSlot =
                    (1024 - (ClusterProperty.id - 1) * size) >= size ?
                            (ClusterProperty.fromSlot + size - 1) : 1024 - 1;
        }
        server.createServer(TCPProperty.TCPPort);
    }
}
