package com.derrick.spring.client;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * Created by Dre on 2016/11/30.
 */
public class ClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

    public ClientHttpRequestFactory() {
        super.setConnectTimeout(5000);
        super.setReadTimeout(10000);
    }

    public ClientHttpRequestFactory(CloseableHttpClient closeableHttpClient) {
        super(closeableHttpClient);
    }
}
