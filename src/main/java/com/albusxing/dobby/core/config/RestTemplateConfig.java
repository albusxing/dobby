package com.albusxing.dobby.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * RestTemplate 配置
 * @author liguoqing
 */
@Configuration
public class RestTemplateConfig {


    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(20000);
        factory.setReadTimeout(20000);
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }




}
