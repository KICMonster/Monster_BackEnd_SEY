package com.monster.luv_cocktail.domain.service;

import com.monster.luv_cocktail.domain.dto.GoogleUserInfo;
import com.monster.luv_cocktail.domain.dto.KakaoUserInfo;
import com.monster.luv_cocktail.domain.dto.NaverUserInfo;
import com.monster.luv_cocktail.domain.dto.UserInfo;
import com.monster.luv_cocktail.domain.enumeration.LoginType;
import com.monster.luv_cocktail.domain.enumeration.Role;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SocialLoginService {
    private final RestTemplate restTemplate;
    private final SocialLoginSuccessHandler socialLoginSuccessHandler;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUrl;
    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUrl;
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfoUrl;

    public SocialLoginService(RestTemplate restTemplate, SocialLoginSuccessHandler socialLoginSuccessHandler) {
        this.restTemplate = restTemplate;
        this.socialLoginSuccessHandler = socialLoginSuccessHandler;
    }

    public UserInfo getUserInfoFromKakao(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity((Object)null, headers);
        ResponseEntity<KakaoUserInfo> response = this.restTemplate.exchange(this.kakaoUserInfoUrl, HttpMethod.GET, entity, KakaoUserInfo.class, new Object[0]);
        System.out.println("Response: " + response + ", Request URL: " + this.kakaoUserInfoUrl);
        KakaoUserInfo kakaoUserInfo = (KakaoUserInfo)response.getBody();
        System.out.println("KakaoUserInfo: " + kakaoUserInfo);
        System.out.println("kakaoUserInfo: " + kakaoUserInfo.getKakao_account().getEmail());
        System.out.println("kakaoUserInfo: " + kakaoUserInfo.getKakao_account().getProfile().getNickname());
        if (kakaoUserInfo != null) {
            UserInfo userInfo = UserInfo.mapKakaoUserInfoToUserInfo(kakaoUserInfo);
            userInfo.setRoles(Arrays.asList(Role.USER));
            userInfo.setLoginTypes(Collections.singletonList(LoginType.Y));
            return userInfo;
        } else {
            return null;
        }
    }

    public UserInfo getUserInfoFromNaver(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity(headers);
        String url = this.naverUserInfoUrl;
        ResponseEntity<NaverUserInfo> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, NaverUserInfo.class, new Object[0]);
        System.out.println("url: " + url);
        System.out.println("response: " + response);
        NaverUserInfo naverUserInfo = (NaverUserInfo)response.getBody();
        System.out.println("NaverUserInfo: " + naverUserInfo);
        if (naverUserInfo != null && naverUserInfo.getResponse() != null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(naverUserInfo.getResponse().getEmail());
            userInfo.setName(naverUserInfo.getResponse().getName());
            userInfo.setRoles(Arrays.asList(Role.USER));
            userInfo.setLoginTypes(Collections.singletonList(LoginType.Y));
            return userInfo;
        } else {
            return null;
        }
    }

    public UserInfo getUserInfoFromGoogle(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity(headers);
        String url = this.googleUserInfoUrl;
        System.out.println("url: " + url);
        ResponseEntity<GoogleUserInfo> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, GoogleUserInfo.class, new Object[0]);
        GoogleUserInfo googleUserInfo = (GoogleUserInfo)response.getBody();
        if (googleUserInfo != null) {
            UserInfo userInfo = UserInfo.mapGoogleUserInfoToUserInfo(googleUserInfo);
            userInfo.setRoles(Collections.singletonList(Role.USER));
            userInfo.setLoginTypes(Collections.singletonList(LoginType.Y));
            return userInfo;
        } else {
            return null;
        }
    }
}
