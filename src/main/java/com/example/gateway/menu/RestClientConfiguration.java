package com.example.gateway.menu;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {

    @LoadBalanced
    @Bean
    RestOperations restOperations() {
        return new RestTemplate();
    }
}
