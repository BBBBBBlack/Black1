package com.example.demo01.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    Long id;
    Long goodsId;
    Long rootId;
    String content;
    Long toCommentUserId;
    Long createBy;
    Date createTime;
}
