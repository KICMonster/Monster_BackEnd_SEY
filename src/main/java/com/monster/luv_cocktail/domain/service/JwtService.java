package com.monster.luv_cocktail.domain.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
    @Value("${jwt.secret-key}")
    private String jwtSecret;

    public JwtService() {
    }

    public String extractEmailFromToken(String token) {
        Claims claims = (Claims)Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }
}

