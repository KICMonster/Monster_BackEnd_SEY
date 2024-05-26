package com.monster.luv_cocktail.domain.filter;

import com.monster.luv_cocktail.domain.dto.UserInfo;
import com.monster.luv_cocktail.domain.entity.Member;
import com.monster.luv_cocktail.domain.enumeration.Role;
import com.monster.luv_cocktail.domain.provider.JWTProvider;
import com.monster.luv_cocktail.domain.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final MemberRepository memberRepository;
    private final JWTProvider jwtProvider;

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return authorization != null && authorization.startsWith("Bearer ") ? authorization.substring(7) : null;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("Entering JwtAuthenticationFilter doFilterInternal");
            String token = this.parseBearerToken(request);
            if (token != null && !this.jwtProvider.validateToken(token)) {
                String refreshToken = request.getHeader("Refresh");
                if (refreshToken != null && this.jwtProvider.validateRefreshToken(refreshToken)) {
                    Claims claims = this.jwtProvider.parseClaims(refreshToken);
                    String username = claims.getSubject();
                    String newAccessToken = this.jwtProvider.createAccessToken(username, (String)claims.get("auth", String.class));
                    response.setHeader("Authorization", "Bearer " + newAccessToken);
                    Authentication auth = this.jwtProvider.getAuthentication(this.extractUserInfoFromClaims(claims));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.info("Authenticated user with new token: {}", auth.getPrincipal());
                }
            } else if (token != null) {
                if (this.jwtProvider.validateToken(token)) {
                    Claims claims = this.jwtProvider.parseClaims(token);
                    if (claims != null) {
                        UserInfo userInfo = this.extractUserInfoFromClaims(claims);
                        Authentication auth = this.jwtProvider.getAuthentication(userInfo);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        log.info("Authenticated user: {}", auth.getPrincipal());
                        this.updateUserInfo(claims);
                    }
                } else {
                    log.warn("Invalid access token");
                }
            }

            log.info("SecurityContextHolder authentication: {}", SecurityContextHolder.getContext().getAuthentication());
            filterChain.doFilter(request, response);
        } catch (Exception var10) {
            Exception exception = var10;
            log.error("Authentication error", exception);
            response.setStatus(401);
        }

    }

    private UserInfo extractUserInfoFromClaims(Claims claims) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail((String)claims.get("sub", String.class));
        String roleString = (String)claims.get("auth", String.class);
        roleString = roleString.replace("ROLE_", "");
        Role role = Role.valueOf(roleString);
        userInfo.setRoles(Collections.singletonList(role));
        return userInfo;
    }

    private void updateUserInfo(Claims claims) {
        String email = (String)claims.get("email", String.class);
        if (email != null) {
            Optional<Member> optionalMember = this.memberRepository.findByEmail(email);
            if (optionalMember.isPresent()) {
                Member member = (Member)optionalMember.get();
                member.setName((String)claims.get("name", String.class));
                member.setRole((Role)claims.get("roles", Role.class));
                this.memberRepository.save(member);
                log.info("Member information updated: {}", member);
            }
        }

    }

    public JwtAuthenticationFilter(final MemberRepository memberRepository, final JWTProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }
}