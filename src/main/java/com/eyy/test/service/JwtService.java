package com.eyy.test.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
    @Value("${jwt.secret-key}")
    private String jwtSecret;

    public String extractEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject(); // 토큰에서 이메일 정보 추출
    }// 기존회원 토큰 유효성 검증로직 추가

//    public Long extractMemberIdFromToken(String token) {
//        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
//        return Long.parseLong(claims.getId()); // 토큰에서 멤버 아이디 정보 추출
//    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
