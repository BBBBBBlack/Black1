package com.example.demo01.Service.Impl;

import com.example.demo01.Domain.Check;
import com.example.demo01.Domain.Goods;
import com.example.demo01.Domain.LoginUser;
import com.example.demo01.Domain.Result;
import com.example.demo01.Mapper.GoodsMapper;
import com.example.demo01.Mapper.SubmitMapper;
import com.example.demo01.MyException.WrongTypeException;
import com.example.demo01.Service.GoodsService;
import com.example.demo01.Util.SecurityUtil;
import com.example.demo01.Util.UpLoadFileUtil;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SubmitMapper submitMapper;
    @Override
    public Result guessYourPreference() {
        List<Goods> yourPreference = goodsMapper.guessYourPreference();
        return new Result(200,"查询猜你喜欢成功",yourPreference);
    }
    @Override
    public Result showTodayGoods() {
        List<Goods> todayGoods = goodsMapper.showTodayGoods();
        return new Result(200,"查询今日上新成功",todayGoods);
    }

    @Override
    public Result showGoodsMessage(Long goodsId) {
        Goods goods = goodsMapper.showGoodsMessage(goodsId);
        return new Result(200,"查询商品信息成功",goods);
    }

    @Override
    public Result showAllGoods() {
        List<Goods> goodsList = goodsMapper.showAllGoods();
        return new Result(200,"查询所有商品成功",goodsList);
    }

    @Override
    public Result findGoods(String keyWords) {
        List<Goods> goodsList = goodsMapper.findGoods(keyWords);
        if(goodsList.isEmpty()){
            return new Result(200,"无相关商品");
        }
        return new Result(200,"查询商品成功",goodsList);
    }

    @Override
    public Result submitGoods(Goods goods, MultipartFile file) {
        String virUrl;
        try {
            virUrl=UpLoadFileUtil.upLoadProImag(file);
        } catch (Exception e) {
            return new Result(400,e.getMessage());
        }
        goods.setGoodsPicture(virUrl);
        goodsMapper.addGoods(goods);//将商品加入商品表中
        Long goodsId;
        goodsId= goodsMapper.findGoodsIdByGoodsName(goods.getGoodsName());//找到商品id
        Long userId = SecurityUtil.getNowUserId();
        goodsMapper.addSubmit(goodsId,userId);//在提交表中添加信息
        return new Result(200,"上传成功",virUrl);
    }

    @Override
    public Result deleteSubmittedGoods(Long goodsId) {
        Long userId = SecurityUtil.getNowUserId();
        int count = submitMapper.findSubmitted(goodsId, userId);
        if(count==0){
            return new Result(200,"无处理权限");
        }
        submitMapper.delSubmitted(goodsId,userId);
        return new Result(200,"删除成功");
    }

    @Override
    public Result resetSubmitted(Goods goods,MultipartFile file) {
        Long userId = SecurityUtil.getNowUserId();
        int count = submitMapper.findAbleSubmitted(goods.getId(), userId);
        if(count==0){
            return new Result(200,"无修改权限");
        }
        String virUrl;
        try {
            virUrl=UpLoadFileUtil.upLoadProImag(file);
        } catch (Exception e) {
            return new Result(400,e.getMessage());
        }
        goods.setGoodsPicture(virUrl);
        goodsMapper.updateSubmitted(goods);
        return new Result(200,"修改成功");
    }


}
