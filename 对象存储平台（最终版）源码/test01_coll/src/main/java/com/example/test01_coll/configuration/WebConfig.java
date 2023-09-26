//package com.example.test01_coll.configuration;
//
//import io.undertow.UndertowOptions;
//import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WebConfig {
//    @Bean
//    UndertowServletWebServerFactory undertowServletWebServerFactory() {
//        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
//        factory.addBuilderCustomizers(builder -> builder
//                .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
//                .setBufferSize(1024 * 16)
//                .setDirectBuffers(true));
//        return factory;
//    }
//}
