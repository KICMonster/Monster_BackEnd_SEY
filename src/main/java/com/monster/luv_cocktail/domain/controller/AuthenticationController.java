package com.monster.luv_cocktail.domain.controller;

import com.monster.luv_cocktail.domain.dto.JwtTokenDTO;
import com.monster.luv_cocktail.domain.dto.SocialLoginRequest;
import com.monster.luv_cocktail.domain.dto.UserInfo;
import com.monster.luv_cocktail.domain.provider.JWTProvider;
import com.monster.luv_cocktail.domain.repository.MemberRepository;
import com.monster.luv_cocktail.domain.repository.TokenRepository;
import com.monster.luv_cocktail.domain.service.SocialLoginService;
import com.monster.luv_cocktail.domain.service.SocialLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = {"https://localhost:5174"}
)
@RequestMapping({"/api"})
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

    @PostMapping({"/authenticate"})
    public ResponseEntity<JwtTokenDTO> handleSocialLogin(@RequestBody SocialLoginRequest request) {
        String accessToken = request.getAccessToken();
        String service = request.getService();
        System.out.println(service);
        System.out.println(accessToken);
        UserInfo userInfo = null;
        if ("kakao".equalsIgnoreCase(service)) {
            userInfo = this.socialLoginService.getUserInfoFromKakao(accessToken);
        } else if ("naver".equalsIgnoreCase(service)) {
            userInfo = this.socialLoginService.getUserInfoFromNaver(accessToken);
        } else {
            userInfo = this.socialLoginService.getUserInfoFromGoogle(accessToken);
        }

        Authentication authentication = this.jwtProvider.getAuthentication(userInfo);
        System.out.println("토큰로직 권한확인");
        JwtTokenDTO jwtToken = this.jwtProvider.generateToken(authentication);
        System.out.println(jwtToken);
        this.socialLoginSuccessHandler.handleSuccess(userInfo, jwtToken);
        return ResponseEntity.ok(jwtToken);
    }
}