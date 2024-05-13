package com.eyy.test.config.sequrity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Arrays.asList("https://localhost:5174"));
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(Arrays.asList("*"));
                    config.setAllowCredentials(true);

                    cors.configurationSource(request -> config);
                })
                .csrf(CsrfConfigurer::disable) // CSRF 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth2Login -> oauth2Login
                        .defaultSuccessUrl("/oauth2/success", true) // OAuth2 인증 성공 후 리디렉션할 URL 지정
                        .failureUrl("/login?error=true") // 로그인 실패 시 리디렉션할 URL 지정
                        .permitAll()
                )
                .formLogin(formLogin -> formLogin // 폼 로그인 사용
                        .loginPage("/login") // 사용자 리디렉션을 위한 커스텀 로그인 페이지 지정
                        .permitAll() // 로그인 페이지는 모든 사용자에게 허용
                        .defaultSuccessUrl("/home", true) // 로그인 성공 후 리디렉션할 URL 지정
                        .failureUrl("/login?error=true") // 로그인 실패 시 리디렉션할 URL 지정
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/api/authenticate","/join/emails/verification-requests"
                                ,"/join/emails/verifications","/join/submit").permitAll() // Preflight 요청 허용
                        .requestMatchers(HttpMethod.DELETE, "/join/withdraw").permitAll() // DELETE 요청은 인증 필요
                        .requestMatchers("/api/authenticate", "/join/emails/verification-requests"
                                ,"/join/emails/verifications","/join/submit").permitAll() // /api/authenticate 경로에 대해 모든 요청 허용
                        .anyRequest().authenticated() // 다른 요청은 인증 필요
                );


        return http.build();
    }

    // Preflight 요청인지 확인하는 메서드는 다른 곳에서 사용할 수 있도록 클래스 밖에 정의하거나, 필요에 따라 사용하는 곳에서 직접 조건을 확인해야 합니다.
}