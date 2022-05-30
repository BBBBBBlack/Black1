package com.example.demo01.Service;

import com.example.demo01.Domain.Bill;
import com.example.demo01.Domain.Result;

public interface BillService {
    Result addBill(Long goodsId,int number);
    Result showYourBills();
    Result showOthersBills();
    Result HandleOtherBills(Long billId,Long isAgree);
    Result delBill(Long billId);
}
