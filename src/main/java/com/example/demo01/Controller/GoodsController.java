package com.example.demo01.Controller;

import com.example.demo01.Domain.Goods;
import com.example.demo01.Domain.Result;
import com.example.demo01.Service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/goods")
@Transactional
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    //今日上新
    @RequestMapping("/showTodayGoods")
    public Result showTodayGoods(){
        return goodsService.showTodayGoods();
    }
    //猜你喜欢
    @RequestMapping("/guessYourPreference")
    public Result guessYourPreference(){
        return goodsService.guessYourPreference();
    }
    //展示单个商品详细信息
    @RequestMapping("/showGoodsMessage")
    public Result showGoodsMessage(@RequestBody Map<String,Long> map){
        Long goodsId = map.get("goodsId");
        return goodsService.showGoodsMessage(goodsId);
    }
    //展示所有商品列表
    @RequestMapping("/showAllGoods")
    public Result showAllGoods(){
        return goodsService.showAllGoods();
    }
    //根据关键词模糊查询商品
    @RequestMapping("/findGoods")
    public Result findGoods(@RequestBody Map<String,String> map){
        String keyWords = map.get("keyWords");
        System.out.println(keyWords);
        return goodsService.findGoods(keyWords);
    }
    //提交商品
    @RequestMapping("/submitGoods")
    @PreAuthorize("hasAuthority('common:sell')")
    public Result submitGoods(@RequestParam("goodsName")String goodsName,
                              @RequestParam("introduction")String introduction,
                              @RequestParam("price")int price,
                              @RequestParam("number")int number,
                              @RequestParam("file")MultipartFile file){
        Goods goods=new Goods(null,goodsName,introduction,price,number,null,null);
        return goodsService.submitGoods(goods,file);
    }
    //删除提交商品
    @RequestMapping("/deleteSubmittedGoods")
    @PreAuthorize("hasAuthority('common:sell')")
    public Result deleteSubmittedGoods(@RequestBody Map<String,Long> map){
        Long goodsId = map.get("goodsId");
        return goodsService.deleteSubmittedGoods(goodsId);
    }
    //修改提交商品
    @RequestMapping("/resetSubmitted")
    @PreAuthorize("hasAuthority('common:sell')")
    public Result resetSubmitted(@RequestParam("goodsId") Long goodsId,
                                 @RequestParam("goodsName")String goodsName,
                                 @RequestParam("introduction")String introduction,
                                 @RequestParam("price")int price,
                                 @RequestParam("number")int number,
                                 @RequestParam("file")MultipartFile file){
        Goods goods=new Goods(goodsId,goodsName,introduction,price,number,null,null);
        return goodsService.resetSubmitted(goods,file);
    }
    //显示商品审核信息
    @RequestMapping("/showSuggest")
    @PreAuthorize("hasAuthority('common:show_suggest')")
    public Result showSuggest(){
        return goodsService.showSuggest();
    }
}
