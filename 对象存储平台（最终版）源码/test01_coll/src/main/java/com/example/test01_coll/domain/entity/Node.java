package com.example.test01_coll.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Serializable {
    private Integer id;//节点id
    private String ip;
    private Integer apiPort;
    private Integer TCPPort;
    private Integer fromSlot;
    private Integer toSlot;
    private Slave slave;
}