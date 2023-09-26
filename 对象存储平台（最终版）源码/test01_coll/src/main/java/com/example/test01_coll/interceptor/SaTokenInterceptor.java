package com.example.test01_coll.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.example.test01_coll.property.ClusterProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Order(5)
public class SaTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<Integer,String> map= ClusterProperty.SocketMap;

        if(map!=null){
            String path=request.getRemoteAddr()+":"+request.getRemotePort();

            //遍历map后判断与request的路由是否一致
            AtomicBoolean flag=new AtomicBoolean(false);
            map.forEach((k,v)->{
                System.out.println(v);
                if(v.equals(path)){
                    flag.set(true);
                }
            });

            if(flag.get()){
                return true;
            }
        }

        StpUtil.checkLogin();

        return true;
    }
}
