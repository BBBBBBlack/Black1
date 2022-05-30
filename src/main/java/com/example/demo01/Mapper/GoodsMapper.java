package com.example.demo01.Mapper;

import com.example.demo01.Domain.Bill;
import com.example.demo01.Domain.Check;
import com.example.demo01.Domain.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GoodsMapper {
    Goods showGoodsMessage(Long goodsId);
    List<Goods> showAllGoods();
    List<Goods> findGoods(String keyWords);
    void addGoods(Goods goods);
    Long findGoodsIdByGoodsName(String goodsName);
    void updateSubmitted(Goods goods);
    void addSubmit(Long goodsId,Long userId);
    void dealGoods(@Param("goodsId") Long goodsId,@Param("billNumber") int billNumber);
    List<Goods> showTodayGoods();
    List<Goods> guessYourPreference();
}
