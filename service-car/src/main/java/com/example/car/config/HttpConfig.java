package com.example.car.config;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
@Configuration
public class HttpConfig {
    @Bean RestTemplate restTemplate() {
        var factory=new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);factory.setReadTimeout(5000);
        return new RestTemplate(factory);
    }
}
