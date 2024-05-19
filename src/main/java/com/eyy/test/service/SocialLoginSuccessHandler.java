package com.eyy.test.service;

import com.eyy.test.dto.JwtTokenDTO;
import com.eyy.test.dto.UserInfo;
import com.eyy.test.entity.JwtToken;
import com.eyy.test.entity.Member;
import com.eyy.test.repository.MemberRepository;
import com.eyy.test.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SocialLoginSuccessHandler {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenRepository tokenRepository;
    public void handleSuccess(UserInfo userInfo, JwtTokenDTO jwtToken) {
        // 사용자 정보를 Member 엔티티로 변환 후 저장 또는 업데이트
        Optional<Member> existingMemberOptional = memberRepository.findByEmail(userInfo.getEmail());

        Member newMember = null;
        if (existingMemberOptional.isPresent()) {
            // 이미 회원 정보가 존재하는 경우
            Member existingMember = existingMemberOptional.get();
            // 기존 회원 정보 업데이트
            existingMember.setName(userInfo.getName());
            existingMember.setRole(userInfo.getRoles().get(0));
            existingMember.setLoginType(userInfo.getLoginTypes().get(0));
            // 생년월일, 성별, 전화번호 등 필요한 정보 업데이트

            // 회원 정보 업데이트
            memberRepository.save(existingMember);
        } else {
            // 새로운 회원 정보 저장
            newMember = new Member();
            newMember.setEmail(userInfo.getEmail());
            newMember.setName(userInfo.getName());
            newMember.setRole(userInfo.getRoles().get(0));
            newMember.setLoginType(userInfo.getLoginTypes().get(0));
            memberRepository.save(newMember);
        }

        // JWT 토큰 저장
        JwtToken tokenEntity = new JwtToken();
        tokenEntity.setJwtAccessToken(jwtToken.getJwtAccessToken());
        tokenEntity.setRefreshToken(jwtToken.getRefreshToken());
        tokenEntity.setGrantType(jwtToken.getGrantType());
        tokenEntity.setExpiresIn(jwtToken.getExpiresIn());
        tokenEntity.setMember(existingMemberOptional.orElse(newMember)); // 회원 정보와의 관계 설정

        tokenRepository.save(tokenEntity);

        System.out.println("사용자 정보와 JWT 토큰이 데이터베이스에 저장되었습니다.");
    }
}

