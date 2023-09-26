package com.example.test01_coll.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientUtils {
    //  客户端从服务端读取数据的超时时间
    private static final int HTTP_TIMEOUT = 500;
    //  客户端与服务器建立连接的超时时间
    private static final int HTTP_CON_TIMEOUT = 200;
    //  客户端从连接池中获取连接的超时时间
    private static final int HTTP_CON_REQ_TIMEOUT = 100;
    //  路由的默认最大连接
    private static final int HTTP_MAX_PERROUTE = 500;
    //  整个连接池连接的最大值
    private static final int HTTP_MAX_TOTAL = 1000;
    public static CloseableHttpClient httpClient = null;

    static {
        //  创建连接池管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        //  设置socket配置
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .build();
        connManager.setDefaultSocketConfig(socketConfig);
        connManager.setMaxTotal(HTTP_MAX_TOTAL);
        connManager.setDefaultMaxPerRoute(HTTP_MAX_PERROUTE);
        //  设置获取连接超时时间、建立连接超时时间、从服务端读取数据的超时时间
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(HTTP_TIMEOUT)
                .setConnectTimeout(HTTP_CON_TIMEOUT)
                .setConnectionRequestTimeout(HTTP_CON_REQ_TIMEOUT)
                .build();
        //  创建httpclient实例
        httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
    }
}