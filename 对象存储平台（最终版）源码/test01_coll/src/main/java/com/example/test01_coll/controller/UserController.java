package com.example.test01_coll.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.test01_coll.service.impl.UserService;
import com.example.test01_coll.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登录
     * @param email  邮箱
     * @param password 密码
     */
    @RequestMapping("/login")
    public ResultUtil login(String email, String password){
        return userService.login(email,password);
    }


    /**
     * 注册
     */
    @RequestMapping(value = "/logout")
    public ResultUtil logout(){
        StpUtil.logout(StpUtil.getLoginId().toString());

        return new ResultUtil(200,"退出成功");
    }

    /**
     * 注册
     * @param email 邮箱
     * @param password 密码
     * @param code 验证码
     */
    @RequestMapping(value = "/logon")
    public ResultUtil logon(String email, String password, String code){
        return userService.logon(email,password,code);
    }

    /**
     * 更改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @RequestMapping(value = "/update/password")
    public ResultUtil updatePassword(String oldPassword, String newPassword){
        if(userService.updatePassword(oldPassword,newPassword)){
            return new ResultUtil(200,"修改成功");
        }else{
            return new ResultUtil(500,"旧密码错误");
        }
    }

    /**
     * 更改昵称
     * @param newName 新昵称
     */
    @RequestMapping(value = "/update/name")
    public ResultUtil updateName(String newName){
        userService.updateName(newName);

        return new ResultUtil(200,"修改成功");
    }

    /**
     * 发送验证码
     * @param email 邮箱
     */
    @RequestMapping("/sendMail")
    public ResultUtil sendEmail(@RequestParam("email")String email){
        return userService.sendEmail(email);
    }

    /**
     * 更改邮箱
     * @param newEmail 新邮箱
     * @param code 验证码
     */
    @RequestMapping("/changeMail")
    public ResultUtil changeEmail(String newEmail, String code){
        return userService.changeEmail(newEmail,code);
    }

    /**
     * 判断这个用户存不存在，在不在这个bucket里头
     * @param bucketId 桶id
     * @param email 邮箱
     */
    @RequestMapping("/judge")
    public ResultUtil judgeUser(int bucketId, String email){
        return userService.judgeUser(bucketId,email);
    }
}
