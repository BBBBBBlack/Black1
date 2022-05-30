package com.example.demo01.Service.Impl;

import com.example.demo01.Domain.Bill;
import com.example.demo01.Domain.LoginUser;
import com.example.demo01.Domain.Result;
import com.example.demo01.Domain.User;
import com.example.demo01.Mapper.BillMapper;
import com.example.demo01.Mapper.GoodsMapper;
import com.example.demo01.Mapper.UserMapper;
import com.example.demo01.Service.BillService;
import com.example.demo01.Util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class BillServiceImpl implements BillService {
    @Autowired
    BillMapper billMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Override
    public Result addBill(Long goodsId,int number) {
        Long userId = SecurityUtil.getNowUserId();
        try {
            billMapper.addBill(userId,goodsId,number);
        } catch (DuplicateKeyException e) {
            return new Result(200,"订单已提交过了");
        }
        return new Result(200,"订单提交成功");
    }
    @Override
    public Result delBill(Long billId){
        Long userId = SecurityUtil.getNowUserId();
        //判断订单是否存在且由当前用户提交
        Integer cnt = billMapper.findBill(userId, billId);
        if(cnt==0){
            return new Result(200,"订单不存在或无操作权限");
        }
        billMapper.delBill(billId);
        return new Result(200,"订单删除成功");
    }
    @Override
    public Result showYourBills() {

        Long userId = SecurityUtil.getNowUserId();
        List<Bill> billList = billMapper.showYourBills(userId);
//        User user=new User();
//        user.setUserName(loginUser.getUser().getUserName());
//        user.setPhoneNumber(loginUser.getUser().getPhoneNumber());
//        Iterator<Bill> iterator=billList.iterator();
//        while(iterator.hasNext()){
//            iterator.next().setUser(user);
//        }
        return new Result(200,"查询订单成功",billList);
    }

    @Override
    public Result showOthersBills() {
        Long userId = SecurityUtil.getNowUserId();
        List<Bill> othersBills = billMapper.showOthersBills(userId);
        return new Result(200,"查询你的商品订单成功",othersBills);
    }

    @Override
    public Result HandleOtherBills(Long billId,Long isAgree) {
        Bill bill = billMapper.findBillByBillId(billId);
        Integer status = bill.getStatus();
        if(status!=0){
            return new Result(200,"订单已经处理过了");
        }
        Long goodsId = bill.getGoods().getId();//获得该订单订购商品的id
        Long userId = SecurityUtil.getNowUserId();//获取当前用户id
        //判断该商品是否由当前用户所提交，且审核完毕
        Integer count = userMapper.judgeUserBillHandleAuthority(userId, goodsId);
        if(count==0){
            return new Result(200,"无处理权限");
        }
        if(isAgree!=0){
            Integer billNumber = bill.getNumber();
            Integer goodsNumber = bill.getGoods().getNumber();
            //判断商品数是否大于买家所订购数
            if(billNumber>goodsNumber){
                return new Result(200,"商品数量不足");
            }
            //补充订单信息
            billMapper.dealBill(billId,1);
            //减少所订购商品数量
            goodsMapper.dealGoods(goodsId,billNumber);
        }else{
            billMapper.dealBill(billId,-1);
        }
        return new Result(200,"处理完毕");
    }
}
