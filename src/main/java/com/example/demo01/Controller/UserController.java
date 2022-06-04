package com.example.demo01.Controller;

import com.example.demo01.Domain.Check;
import com.example.demo01.Domain.Result;
import com.example.demo01.Domain.User;
import com.example.demo01.Service.GoodsService;
import com.example.demo01.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @PostMapping("/user/login")
    public Result login(@RequestBody Map<String,String> map){
        String userName = map.get("userName");
        String password = map.get("password");
        User user=new User();
        user.setUserName(userName);
        user.setPassword(password);
        return userService.login(user);
    }

    @RequestMapping("/user/logout")
    public Result logout(){
        return userService.logout();
    }
    @PostMapping("/user/register")
    public Result register(@RequestBody User user){
        return userService.register(user.getUserName(),user.getPassword());
    }
    @RequestMapping("/user/showUserMessage")
    public Result showUserMessage(){
        return userService.showUserMessage();
    }

    @PostMapping("/user/addProfilePicture")
    public Result addProfilePicture(@RequestParam("file") MultipartFile file){
//        System.out.println("1111111111");
        return userService.addProfilePicture(file);
    }
    @RequestMapping("/user/addUserMessage")
    public Result addUserMessage(@RequestBody User user){
        return userService.addUserMessage(user);
    }

}
