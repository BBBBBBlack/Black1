package com.example.test01_coll.configuration;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.example.test01_coll.interceptor.MaxAuthInterceptor;
import com.example.test01_coll.interceptor.ReadAuthInterceptor;
import com.example.test01_coll.interceptor.SaTokenInterceptor;
import com.example.test01_coll.interceptor.WriteAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Resource
    ReadAuthInterceptor readAuthInterceptor;

    @Resource
    WriteAuthInterceptor writeAuthInterceptor;

    @Resource
    MaxAuthInterceptor maxAuthInterceptor;

    @Resource
    SaTokenInterceptor saTokenInterceptor;


     //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(saTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/setPath","/user/login","/user/logon","/test/**","/user/sendMail");



        //注册读权限拦截器
        registry.addInterceptor(readAuthInterceptor)
                .addPathPatterns("/get/**");

        //注册写权限拦截器
        registry.addInterceptor(writeAuthInterceptor)
                .addPathPatterns("/put/**")
                .excludePathPatterns("/put/checkShard");

        //注册最高权限拦截器
        registry.addInterceptor(maxAuthInterceptor)
                .addPathPatterns("/bucket/user/delete","/bucket/update/authority","/bucket/update/authority","/bucket/update/name",
                        "/bucket/delete","/bucket/user/add","/bucket/user/setAuth","/bucket/user/list","/bucket/recovery");

    }

}