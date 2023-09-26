package com.example.test01_coll.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class Bucket implements Serializable {
    private String id;
    private Set<String> fileSet;//文件名列表

    public Bucket(String id) {
        this.id = id;
    }

}
