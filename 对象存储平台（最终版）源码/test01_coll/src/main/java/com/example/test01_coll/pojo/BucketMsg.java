package com.example.test01_coll.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BucketMsg implements Serializable {

    /**
     * 桶的名称
     */
    private String name;

    /**
     * 创建者的id
     */
    private String creatorId;

    /**
     * 桶的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 权限类别：1—>公共读，2—>公共读写，3—>私有
     */
    private int authority;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 桶的大小
     */
    @TableField(exist = false)
    private Long bucketSize;
    /**
     * 桶中对象的数量
     */
    @TableField(exist = false)
    private Integer fileNum;

    public BucketMsg(String name, String creatorId, int authority) {
        this.id = null;
        this.name = name;
        this.creatorId = creatorId;
        this.isDelete = false;
        this.authority = authority;
        this.createTime = new Date();
    }

}
