package com.example.test01_coll.domain.entity.Message;

import com.example.test01_coll.domain.entity.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PONGMessage extends Message implements Serializable {

    private Node node;

    @Override
    public Integer getType() {
        return PONGMessage;
    }

    @Override
    public Integer getSequenceId() {
        return -1;
    }
}
