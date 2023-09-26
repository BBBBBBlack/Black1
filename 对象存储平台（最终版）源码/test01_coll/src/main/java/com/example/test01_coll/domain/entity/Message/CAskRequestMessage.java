package com.example.test01_coll.domain.entity.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CAskRequestMessage extends Message {

    private String bucketName;

    private String fileName;


    @Override
    public Integer getType() {
        return CAskRequestMessage;
    }

    @Override
    public Integer getSequenceId() {
        return 1;
    }
}
