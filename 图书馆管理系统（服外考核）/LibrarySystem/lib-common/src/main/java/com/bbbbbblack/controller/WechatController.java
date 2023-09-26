package com.bbbbbblack.controller;

import com.bbbbbblack.domain.Result;
import com.bbbbbblack.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    WechatService wechatService;

//    @RequestMapping("/checkToken")
//    public void checkToken(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
//        wechatService.checkToken(request,response);
//    }
//    @GetMapping("/loadWechatLoginUrl")
//    public void loadWechatLoginUrl(@RequestBody Map<String,Integer> map, HttpServletResponse response) throws Exception {
////        类型：1——扫码，2——链接
//        Integer type = map.get("type");
////        1——登录，2——注册
//        Integer purpose = map.get("purpose");
////        response.sendRedirect("https://www.baidu.com");
//        wechatService.loadWechatLoginUrl(type,purpose,response);
//    }
}
