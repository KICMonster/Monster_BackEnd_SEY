package com.eyy.test.service;

import com.eyy.test.dto.JwtTokenDTO;
import com.eyy.test.dto.UserInfo;
import com.eyy.test.entity.JwtToken;
import com.eyy.test.entity.Member;
import com.eyy.test.repository.MemberRepository;
import com.eyy.test.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocialLoginSuccessHandler {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public void handleSuccess(UserInfo userInfo, JwtTokenDTO jwtToken) {
        // 사용자 정보를 Member 엔티티로 변환 후 저장
        Member member = new Member();
        member.setEmail(userInfo.getEmail());
        member.setName(userInfo.getName());
        member.setRole(userInfo.getRoles().get(0));
        member.setLoginType(userInfo.getLoginTypes().get(0));
        // 여기에 필요한 나머지 필드도 채워넣기
        memberRepository.save(member);

        // JWT 토큰을 JwtToken 엔티티로 변환 후 저장
        JwtToken tokenEntity = new JwtToken();
        tokenEntity.setMember(member); // Member와의 관계 설정
        tokenEntity.setJwtAccessToken(jwtToken.getJwtAccessToken());
        tokenEntity.setRefreshToken(jwtToken.getRefreshToken());
        tokenEntity.setGrantType(jwtToken.getGrantType());
        tokenEntity.setExpiresIn(jwtToken.getExpiresIn());
        // tokenEntity의 나머지 필드도 채워넣기
        tokenRepository.save(tokenEntity);

        System.out.println("사용자 정보와 JWT 토큰이 데이터베이스에 저장되었습니다.");
    }
    public void handleBasicUserSuccess(Member member, JwtTokenDTO jwtToken){
        // 기본 사용자 정보 업데이트 로직
        memberRepository.save(member);

        // JWT 토큰을 JwtToken 엔티티로 변환 후 저장
        JwtToken tokenEntity = new JwtToken();
        tokenEntity.setMember(member); // Member와의 관계 설정
        tokenEntity.setJwtAccessToken(jwtToken.getJwtAccessToken());
        tokenEntity.setRefreshToken(jwtToken.getRefreshToken());
        tokenEntity.setGrantType(jwtToken.getGrantType());
        tokenEntity.setExpiresIn(jwtToken.getExpiresIn());
        // tokenEntity의 나머지 필드도 채워넣기
        tokenRepository.save(tokenEntity);

        System.out.println("사용자 정보가 데이터베이스에 업데이트되었습니다.");
    }
}
