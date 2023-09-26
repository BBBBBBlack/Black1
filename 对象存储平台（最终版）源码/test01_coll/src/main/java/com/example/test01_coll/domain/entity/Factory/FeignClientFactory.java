package com.example.test01_coll.domain.entity.Factory;

import com.example.test01_coll.feign.TestFeign;
import com.example.test01_coll.utils.HttpClientUtils;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Import({FeignClientProperties.FeignClientConfiguration.class})
public class FeignClientFactory {

    @Autowired
    Encoder FeignFormEncoder;

    @Autowired
    Decoder FeignJacksonDecoder;

    @Autowired
    Contract FeignMVCContract;

    @Autowired
    RequestInterceptor FeignInterceptor;
//    @Autowired
//    RequestInterceptor GZIPRequestInterceptor;
//    @Autowired
//    RequestInterceptor GZIPResponseInterceptor;

    /**
     * 缓存所有的Feign客户端
     */
    private volatile static Map<String, Object> feignClientCache = new HashMap<>();

    /**
     * 从Map中获取数据
     */
    @SuppressWarnings("unchecked")
    public <T> T getFeignClient(Class<T> clazz, String baseUrl) {
        if (!feignClientCache.containsKey(baseUrl)) {
            synchronized (FeignClientFactory.class) {
                if (!feignClientCache.containsKey(baseUrl)) {
                    T feignClient = Feign.builder()
//                            .logLevel(Logger.Level.BASIC)
                            .client(new ApacheHttpClient(HttpClientUtils.httpClient))
                            .requestInterceptor(FeignInterceptor)
                            .contract(FeignMVCContract)
                            .decoder(FeignJacksonDecoder)
                            .encoder(FeignFormEncoder)
                            .target(clazz, baseUrl);
                    feignClientCache.put(baseUrl, feignClient);
                }
            }
        }
        return (T) feignClientCache.get(baseUrl);
    }
}
