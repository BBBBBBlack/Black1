package com.example.test01_coll.configuration;

import com.example.test01_coll.property.ClusterProperty;
import feign.Contract;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class FeignConfig {
//
//    @Autowired
//    public FeignClientEncodingProperties feignClientEncodingProperties;

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder FeignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    public Decoder FeignJacksonDecoder() {
        return new JacksonDecoder();
    }

    @Bean
    public Contract FeignMVCContract() {
        return new SpringMvcContract();
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    public RequestInterceptor FeignInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                StringBuffer url = request.getRequestURL();
                String token = request.getHeader("token");
                requestTemplate.header("token", token);
                requestTemplate.header("from", String.valueOf(ClusterProperty.id));
            }
        };
    }

//    @Bean
//    public RequestInterceptor GZIPRequestInterceptor() {
//        Class<FeignContentGzipEncodingInterceptor> GZIPInterceptor = FeignContentGzipEncodingInterceptor.class;
//        Constructor<FeignContentGzipEncodingInterceptor> constructor;
//        try {
//            constructor = GZIPInterceptor.getDeclaredConstructor(FeignClientEncodingProperties.class);
//            constructor.setAccessible(true);
//            return constructor.newInstance(feignClientEncodingProperties);
//        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Bean
//    public RequestInterceptor GZIPResponseInterceptor() {
//        Class<FeignAcceptGzipEncodingInterceptor> GZIPInterceptor = FeignAcceptGzipEncodingInterceptor.class;
//        Constructor<FeignAcceptGzipEncodingInterceptor> constructor;
//        try {
//            constructor = GZIPInterceptor.getDeclaredConstructor(FeignClientEncodingProperties.class);
//            constructor.setAccessible(true);
//            return constructor.newInstance(feignClientEncodingProperties);
//        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
