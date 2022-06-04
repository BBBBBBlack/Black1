package com.example.demo01.Service;

import com.example.demo01.Domain.Check;
import com.example.demo01.Domain.Result;
import com.example.demo01.Domain.Suggest;


public interface AdminService {
    Result checkGoods(Check check);
    Result showAllGoodInfo();
    Result showAllGoodsDetails(Long goodsId);
    Result showBannedUser();
    Result addSuggest(Suggest suggest);
}
