package com.eyy.test.config.sequrity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:5174") // 요청을 허용할 Origin
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 요청 헤더 허용
                .allowCredentials(true) // 인증 정보 허용
                .maxAge(3600); // preflight 요청 최대 캐싱 시간 (초)
    }
}
