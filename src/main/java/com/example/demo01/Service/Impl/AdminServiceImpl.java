package com.example.demo01.Service.Impl;

import com.example.demo01.Domain.Check;
import com.example.demo01.Domain.Goods;
import com.example.demo01.Domain.Result;
import com.example.demo01.Domain.Suggest;
import com.example.demo01.Domain.VO.UserVo;
import com.example.demo01.Mapper.AdminMapper;
import com.example.demo01.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Result checkGoods(Check check) {
        //获取提交商品用户id
        Long userId = adminMapper.getCheckingUserId(check.getGoodsId());
        check.setUserId(userId);
        if(adminMapper.getPassGoodsCount(check)>0){
            return new Result(200, "该商品已经审核过了");
        }
        int goodsList = adminMapper.checkGoods(check);
        adminMapper.addCheck(check);
        if (check.getStatus() == 1) {
                return new Result(200, "审核通过", goodsList);
        } else {
            //获取未通过的审核商品的个数
            int count =adminMapper.getNoPassGoodsCount(check);
            if(count<=5){
                return new Result(200, "审核未通过", goodsList);
            }else {
                adminMapper.upDateUserStatus(check.getUserId());
                return new Result(200, "审核未通过,且审核未通过商品数超过五次，账号锁定", goodsList);
            }
        }
    }
    @Override
    public Result showAllGoodInfo() {
        List<Goods> goodsList = adminMapper.showAllGoodsInfo();
        return new Result(200,"查询所有商品成功",goodsList);
    }

    @Override
    public Result showAllGoodsDetails(Long goodsId) {
        Goods goods = adminMapper.showAllGoodsDetails(goodsId);
        return new Result(200,"查询商品信息成功",goods);
    }

    @Override
    public Result showBannedUser() {
        List<UserVo> users = adminMapper.showBannedUser();
        return new Result(200,"查询用户信息成功",users);
    }

    @Override
    public Result addSuggest(Suggest suggest) {
        adminMapper.addSuggest(suggest);
        return new Result(200,"添加成功");
    }

}
