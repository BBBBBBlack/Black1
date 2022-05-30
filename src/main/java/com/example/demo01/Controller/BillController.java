package com.example.demo01.Controller;

import com.example.demo01.Domain.Result;
import com.example.demo01.Service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BillController {
    @Autowired
    BillService billService;
    @RequestMapping("/bill/addBill")
    @PreAuthorize("hasAuthority('common:buy')")
    public Result addBill(@RequestBody Map<String,Object> map){
        Object obj1 = map.get("goodsId");
        Long goodsId=Long.parseLong(String.valueOf(obj1));
        Object obj2 = map.get("number");
        int number=Integer.parseInt(String.valueOf(obj2));
        return billService.addBill(goodsId,number);
    }
    @RequestMapping("/bill/delBill")
    @PreAuthorize("hasAuthority('common:buy')")
    public Result delBill(@RequestBody Map<String,Long> map){
        Long billId = map.get("billId");
        return billService.delBill(billId);
    }
//    展示当前用户的下单
    @RequestMapping("/bill/showYourBills")
    @PreAuthorize("hasAuthority('common:buy')")
    public Result showYourBills(){
        return billService.showYourBills();
    }
//    展示其他用户对当前卖家所提交商品的订单
    @RequestMapping("/bill/showOthersBills")
    @PreAuthorize("hasAuthority('common:sell')")
    public Result showOthersBills(){
        return billService.showOthersBills();
    }
//    卖家处理商品的订单
    @RequestMapping("/bill/HandleOtherBills")
    @PreAuthorize("hasAuthority('common:sell')")
    public Result HandleOtherBills(@RequestBody Map<String,Long> map){
        Long billId = map.get("billId");
        Long isAgree = map.get("isAgree");
        return billService.HandleOtherBills(billId,isAgree);
    }
}

