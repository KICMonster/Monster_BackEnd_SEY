package com.eyy.test.service;

import com.eyy.test.Enumeration.LoginType;
import com.eyy.test.Enumeration.Role;
import com.eyy.test.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static com.eyy.test.dto.UserInfo.mapGoogleUserInfoToUserInfo;
import static com.eyy.test.dto.UserInfo.mapNaverUserInfoToUserInfo;


@Service
public class SocialLoginService {

    private final RestTemplate restTemplate;
    private final SocialLoginSuccessHandler socialLoginSuccessHandler; // 필드 추가


    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUrl;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUrl;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfoUrl;

    public SocialLoginService(RestTemplate restTemplate , SocialLoginSuccessHandler socialLoginSuccessHandler) {
        this.restTemplate = restTemplate;
        this.socialLoginSuccessHandler = socialLoginSuccessHandler; // 주입
    }

    public UserInfo getUserInfoFromKakao(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(null, headers); // 오타 수정 및 본문 변경

        ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(
                kakaoUserInfoUrl, HttpMethod.GET, entity, KakaoUserInfo.class);
        System.out.println("Response: " + response + ", Request URL: " + kakaoUserInfoUrl);
        KakaoUserInfo kakaoUserInfo = response.getBody();
        System.out.println("KakaoUserInfo: " + kakaoUserInfo);
        System.out.println("kakaoUserInfo: " + kakaoUserInfo.getKakao_account().getEmail());
        System.out.println("kakaoUserInfo: " + kakaoUserInfo.getKakao_account().getProfile().getNickname());
        if (kakaoUserInfo != null) {
            // KakaoUserInfo에서 UserInfo로 변환
            UserInfo userInfo = UserInfo.mapKakaoUserInfoToUserInfo(kakaoUserInfo);
            // 권한 정보 설정 (예시: KAKAO_USER 권한 부여)
            userInfo.setRoles(Arrays.asList(Role.USER)); // Role 열거형 상수를 List에 추가

            userInfo.setLoginTypes(Collections.singletonList(LoginType.Y));


            return userInfo;
        }

        return null; // 또는 적절한 예외 처리
    }

    public UserInfo getUserInfoFromNaver(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken); // 네이버 API 호출을 위한 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = naverUserInfoUrl;

        ResponseEntity<NaverUserInfo> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, NaverUserInfo.class); // 네이버 사용자 정보 요청
        System.out.println("url: " + url);
        System.out.println("response: " + response);
        NaverUserInfo naverUserInfo = response.getBody();
        System.out.println("NaverUserInfo: " + naverUserInfo);

        if (naverUserInfo != null && naverUserInfo.getResponse() != null) {
            // NaverUserInfo.NResponse에서 UserInfo로 변환
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(naverUserInfo.getResponse().getEmail());
            userInfo.setName(naverUserInfo.getResponse().getName());
            // 권한 정보 설정 (예시: NAVER_USER 권한 부여)
            userInfo.setRoles(Arrays.asList(Role.USER)); // Role 열거형 상수를 List에 추가

            userInfo.setLoginTypes(Collections.singletonList(LoginType.Y));

            return userInfo;
        }

        return null; // 또는 적절한 예외 처리
    }
public UserInfo getUserInfoFromGoogle(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    String url = googleUserInfoUrl;
    System.out.println("url: " + url);

    ResponseEntity<GoogleUserInfo> response = restTemplate.exchange(
            url, HttpMethod.GET, entity, GoogleUserInfo.class);
    GoogleUserInfo googleUserInfo = response.getBody();

    if (googleUserInfo != null) {
        UserInfo userInfo = mapGoogleUserInfoToUserInfo(googleUserInfo);
        userInfo.setRoles(Collections.singletonList(Role.USER)); // 구글 사용자 권한 부여

        userInfo.setLoginTypes(Collections.singletonList(LoginType.Y));
        return userInfo;
    }

    return null;
}
}

