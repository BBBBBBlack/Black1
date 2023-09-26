package com.example.test01_coll.controller;

import com.example.test01_coll.domain.entity.Node;
import com.example.test01_coll.domain.entity.Slave;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.property.FileProperty;
import com.example.test01_coll.property.TCPProperty;
import com.example.test01_coll.utils.TCPUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController {
    @GetMapping("/pong")
    public Node pong() {
        Slave slave = new Slave(TCPProperty.copyIp, TCPProperty.copyPort);
        return new Node(ClusterProperty.id, TCPUtil.getMyIp(), FileProperty.apiPort,
                TCPProperty.TCPPort, ClusterProperty.fromSlot, ClusterProperty.toSlot, slave);
    }
}
