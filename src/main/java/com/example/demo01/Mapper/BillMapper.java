package com.example.demo01.Mapper;

import com.example.demo01.Domain.Bill;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public interface BillMapper {
    void addBill(@Param("userId")Long userId,@Param("goodsId") Long goodsId,@Param("number") int number)
    throws DuplicateKeyException;
    List<Bill> showYourBills(Long userId);
    List<Bill> showOthersBills(Long userId);
    Bill findBillByBillId(Long billId);
    void dealBill(@Param("billId") Long billId,@Param("status") int status);
    Integer findBill(@Param("userId")Long userId,@Param("billId")Long billId);
    void delBill(Long billId);
}
