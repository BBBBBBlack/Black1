package com.example.test01_coll.cluster;

import com.example.test01_coll.domain.entity.Factory.ClientFactory;
import com.example.test01_coll.domain.entity.Factory.FeignClientFactory;
import com.example.test01_coll.domain.entity.Node;
import com.example.test01_coll.domain.entity.Slave;
import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.feign.TestFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HeartBeat {
    @Autowired
    FeignClientFactory feignClientFactory;
    @Autowired
    ClientFactory clientFactory;

    @Scheduled(cron = "0/24 * * * * ?")
    @Async
    public void heartBeat() throws InterruptedException {
        if (!ClusterProperty.cluster) {
            return;
        }
        Set<Integer> idSet = ClusterProperty.SocketMap.keySet();
        for (Integer id : idSet) {
            String socket = ClusterProperty.SocketMap.get(id);
            Node node = feignClientFactory
                    .getFeignClient(TestFeign.class, "http://" + socket)
                    .pong();
            if (node != null) {
                ClusterProperty.SocketAliveMap.put(node, System.currentTimeMillis());
                ClusterProperty.allNodeSet.add(node);
                clientFactory.createClient(node.getIp(), node.getTCPPort());
            }
//                Client.ping_pong(fromIp, TCPProperty.TCPPort, toIp, toPort);
        }
        Thread.sleep(1000);
        for (Node node : ClusterProperty.allNodeSet) {
            Long time = ClusterProperty.SocketAliveMap.get(node);
            if ((System.currentTimeMillis() - time) > 30000) {
                ClusterProperty.SocketAliveMap.remove(node);
            }
            //节点彻底挂了，启动灾备主机
            else if (!ClusterProperty.SocketAliveMap.containsKey(node)) {
                Slave slave = node.getSlave();
                if (slave != null) {
                    ClusterProperty.allNodeSet.remove(node);
                    node.setIp(slave.getIp());
                    node.setTCPPort(slave.getPort());
                    node.setSlave(null);
                    ClusterProperty.SocketMap.put(node.getId(), node.getIp() + ":" + node.getTCPPort());
                    ClusterProperty.allNodeSet.add(node);
                }
            }
        }
    }
}
