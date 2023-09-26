package com.example.test01_coll.interceptor;

import com.example.test01_coll.property.ClusterProperty;
import com.example.test01_coll.service.impl.GroupService;
import com.example.test01_coll.utils.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Order(999)
public class MaxAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private GroupService groupService;

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

        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String bucketId = (String)pathVariables.get("bucketId");

        if (bucketId == null) {
            bucketId = request.getParameter("bucketId");
        }

        int authority = groupService.getUserAuthority(Integer.parseInt(bucketId));

        if(authority==GroupService.OWNER){
            return true;
        }
        else{
            throw new AuthException(403,"没有权限");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
