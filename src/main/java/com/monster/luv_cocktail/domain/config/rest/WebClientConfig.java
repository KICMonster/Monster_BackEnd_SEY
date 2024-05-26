package com.monster.luv_cocktail.domain.config.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    public WebClientConfig() {
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("https://apis.data.go.kr").build();
    }
}
