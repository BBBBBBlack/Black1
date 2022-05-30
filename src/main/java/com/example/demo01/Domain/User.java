package com.example.demo01.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;//用户id
    private String userName;//用户名
    private String nickName;//用户昵称
    private String password;//用户密码
    private String phoneNumber;//用户联系方式
    private String picture;//用户头像
    private Integer status;//用户状态，1为正常，0为封禁

}
