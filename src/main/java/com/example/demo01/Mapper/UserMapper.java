package com.example.demo01.Mapper;

import com.example.demo01.Domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User findUserByUserName(String userName);
    void addUser(@Param("userName") String userName,@Param("password") String password);
    void addRole(Long userId);
    Integer ifUserExist(String userName);
    void addProfilePicture(Long userId,String virUrl);
    void upDateNickName(String nickName,Long userId);
    void upDatePhoneNumber(String phoneNumber,Long userId);
    //查询当前用户是否有权限处理某订单（查看某商品是否为当前用户提交，且审核完毕，且收到的订单处于未处理状态）
    Integer judgeUserBillHandleAuthority(@Param("userId")Long userId,
                                         @Param("goodsId")Long goodsId);

}
