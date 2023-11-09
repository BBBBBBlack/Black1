package com.example.tea_demo.service;

import com.example.tea_demo.domain.ResponseResult;
import com.example.tea_demo.domain.User;

public interface UserService {
    ResponseResult login(User user);

    ResponseResult logout();
}
