package com.example.test01_coll.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "`group`")
public class Group {
    @TableId(type = IdType.ASSIGN_ID)
    private int id;

    private String userId;

    private int bucketId;

    private int type;

    public Group(String userId,int bucketId,int type){
        this.bucketId=bucketId;
        this.userId=userId;
        this.type=type;
    }

}
