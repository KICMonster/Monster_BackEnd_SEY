package com.monster.luv_cocktail.domain.service;

import com.monster.luv_cocktail.domain.dto.JwtTokenDTO;
import com.monster.luv_cocktail.domain.dto.UserInfo;
import com.monster.luv_cocktail.domain.entity.JwtToken;
import com.monster.luv_cocktail.domain.entity.Member;
import com.monster.luv_cocktail.domain.enumeration.LoginType;
import com.monster.luv_cocktail.domain.enumeration.Role;
import com.monster.luv_cocktail.domain.repository.MemberRepository;
import com.monster.luv_cocktail.domain.repository.TokenRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocialLoginSuccessHandler {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TokenRepository tokenRepository;

    public SocialLoginSuccessHandler() {
    }

    public void handleSuccess(UserInfo userInfo, JwtTokenDTO jwtToken) {
        Optional<Member> existingMemberOptional = this.memberRepository.findByEmail(userInfo.getEmail());
        Member newMember = null;
        if (existingMemberOptional.isPresent()) {
            Member existingMember = existingMemberOptional.get();
            existingMember.setName(userInfo.getName());
            existingMember.setRole(userInfo.getRoles().get(0));
            existingMember.setLoginType(userInfo.getLoginTypes().get(0));
            this.memberRepository.save(existingMember);
        } else {
            newMember = new Member();
            newMember.setEmail(userInfo.getEmail());
            newMember.setName(userInfo.getName());
            newMember.setRole(userInfo.getRoles().get(0));
            newMember.setLoginType(userInfo.getLoginTypes().get(0));
            this.memberRepository.save(newMember);
        }

        JwtToken tokenEntity = new JwtToken();
        tokenEntity.setAccessToken(jwtToken.getJwtAccessToken());
        tokenEntity.setRefreshToken(jwtToken.getRefreshToken());
        tokenEntity.setGrantType(jwtToken.getGrantType());
        tokenEntity.setExpireIn(jwtToken.getExpireIn());
        tokenEntity.setMember(existingMemberOptional.orElse(newMember));
        this.tokenRepository.save(tokenEntity);
        System.out.println("사용자 정보와 JWT 토큰이 데이터베이스에 저장되었습니다.");
    }
}

