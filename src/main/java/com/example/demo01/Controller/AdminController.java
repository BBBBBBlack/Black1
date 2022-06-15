package com.example.demo01.Controller;

import com.example.demo01.Domain.Check;
import com.example.demo01.Domain.Result;
import com.example.demo01.Domain.Suggest;
import com.example.demo01.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    //审核
    @RequestMapping("/checkGoods")
    @PreAuthorize("hasAuthority('admin:check')")
    public Result checkGoods(@RequestBody Check check) {
        System.out.println(check);
        return adminService.checkGoods(check);
    }
    //展示所有商品列表
    @RequestMapping("/showAllGoodsInfo")
    @PreAuthorize("hasAuthority('admin:show_all')")
    public Result showAllGoodsInfo(){
        return adminService.showAllGoodInfo();
    }
    @RequestMapping("/showAllGoodsDetails")
    @PreAuthorize("hasAuthority('admin:show_details')")
    public Result showAllGoodsDetails(@RequestBody Map<String,Long> map){
        Long goodsId = map.get("goodsId");
        return adminService.showAllGoodsDetails(goodsId);
    }
    //展示有提交不合格商品的用户列表
    @RequestMapping("/showBannedUser")
    @PreAuthorize("hasAuthority('admin:show_banned')")
    public Result showBannedUser(){
        return adminService.showBannedUser();
    }
    //添加建议
    @RequestMapping("/addSuggest")
    @PreAuthorize("hasAuthority('admin:add_suggest')")
    public Result addSuggest(@RequestBody Suggest suggest){
        return adminService.addSuggest(suggest);
    }
}
