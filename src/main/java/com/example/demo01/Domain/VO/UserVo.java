package com.example.demo01.Domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    Long userId;
    String userName;
    //用户账号的状态
    Integer userStatus;
    //用户提交商品不合格次数
    Integer count;
}
