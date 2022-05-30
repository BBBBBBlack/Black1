package com.example.demo01.Mapper;

import com.example.demo01.Domain.Check;
import com.example.demo01.Domain.Goods;
import com.example.demo01.Domain.VO.UserVo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdminMapper {
    void upDateUserStatus(Long userId);
    int checkGoods(Check check);
    void addCheck(Check check);
    int getNoPassGoodsCount(Check check);
    int getPassGoodsCount(Check check);
    List<Goods> showAllGoodsInfo();
    Long getCheckingUserId(Long goodsId);
    Goods showAllGoodsDetails(Long goodsId);
    List<UserVo> showBannedUser();
}
