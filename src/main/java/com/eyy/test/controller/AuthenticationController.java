package com.eyy.test.controller;

import com.eyy.test.dto.JwtTokenDTO;
import com.eyy.test.dto.SocialLoginRequest;
import com.eyy.test.dto.UserInfo;
import com.eyy.test.jwt.JWTProvider;
import com.eyy.test.repository.MemberRepository;
import com.eyy.test.repository.TokenRepository;
import com.eyy.test.service.SocialLoginService;
import com.eyy.test.service.SocialLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://localhost:5174")
@RequestMapping("/api") // 고유한 경로로 수정
public class AuthenticationController {

    private final JWTProvider jwtProvider;
    private final SocialLoginService socialLoginService;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private SocialLoginSuccessHandler socialLoginSuccessHandler;

    @Autowired
    public AuthenticationController(JWTProvider jwtProvider, SocialLoginService socialLoginService, SocialLoginSuccessHandler socialLoginSuccessHandler, MemberRepository memberRepository, TokenRepository tokenRepository) {
        this.jwtProvider = jwtProvider;
        this.socialLoginService = socialLoginService;
        this.socialLoginSuccessHandler = socialLoginSuccessHandler;
        this.memberRepository = memberRepository;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtTokenDTO> handleSocialLogin(@RequestBody SocialLoginRequest request) {
        String accessToken = request.getAccessToken();
        String service = request.getService();
        System.out.println(service);
        System.out.println(accessToken);

        UserInfo userInfo = null;
        if ("kakao".equalsIgnoreCase(service)) {
            userInfo = socialLoginService.getUserInfoFromKakao(accessToken);
        } else if ("naver".equalsIgnoreCase(service)) {
            userInfo = socialLoginService.getUserInfoFromNaver(accessToken);
        } else {
            userInfo = socialLoginService.getUserInfoFromGoogle(accessToken);
        }


            Authentication authentication = jwtProvider.getAuthentication(userInfo);
            System.out.println("토큰로직 권한확인");

            // JWT 토큰 생성
            JwtTokenDTO jwtToken = jwtProvider.generateToken(authentication);
            System.out.println(jwtToken);

            // 소셜 로그인 성공 핸들러 호출
            socialLoginSuccessHandler.handleSuccess(userInfo, jwtToken);

            return ResponseEntity.ok(jwtToken);
        }

    }
