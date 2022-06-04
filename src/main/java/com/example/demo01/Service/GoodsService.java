package com.example.demo01.Service;

import com.example.demo01.Domain.Check;
import com.example.demo01.Domain.Goods;
import com.example.demo01.Domain.Result;
import org.springframework.web.multipart.MultipartFile;

public interface GoodsService {
    Result showTodayGoods();
    Result showGoodsMessage(Long goodsId);
    Result showAllGoods();
    Result findGoods(String keyWords);
    Result submitGoods(Goods goods, MultipartFile file);
    Result deleteSubmittedGoods(Long goodsId);
    Result resetSubmitted(Goods goods,MultipartFile file);
    Result guessYourPreference();
    Result showSuggest();
}
