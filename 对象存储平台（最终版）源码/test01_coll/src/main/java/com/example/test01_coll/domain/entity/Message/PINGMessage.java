package com.example.test01_coll.domain.entity.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PINGMessage extends Message implements Serializable {

    private String ip;

    private Integer port;

    @Override
    public Integer getType() {
        return PINGMessage;
    }

    @Override
    public Integer getSequenceId() {
        return -1;
    }
}
