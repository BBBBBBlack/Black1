package com.example.demo01.Util;

import com.example.demo01.Domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static LoginUser getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
        return loginUser;
//        Long userId = loginUser.getUser().getId();
    }
    public static Long getNowUserId(){
        LoginUser loginUser = getLoginUser();
        Long userId = loginUser.getUser().getId();
        return userId;
    }
}
