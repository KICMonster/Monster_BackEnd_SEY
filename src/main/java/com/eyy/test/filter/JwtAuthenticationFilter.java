package com.eyy.test.filter;

import com.eyy.test.Enumeration.Role;
import com.eyy.test.dto.UserInfo;
import com.eyy.test.entity.Member;
import com.eyy.test.jwt.JWTProvider;
import com.eyy.test.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JWTProvider jwtProvider;

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("Entering JwtAuthenticationFilter doFilterInternal");
            String token = parseBearerToken(request);

            if (token != null && !jwtProvider.validateToken(token)) {
                String refreshToken = request.getHeader("Refresh");
                if (refreshToken != null && jwtProvider.validateRefreshToken(refreshToken)) {
                    Claims claims = jwtProvider.parseClaims(refreshToken);
                    String username = claims.getSubject();
                    String newAccessToken = jwtProvider.createAccessToken(username, claims.get("auth", String.class));
                    response.setHeader("Authorization", "Bearer " + newAccessToken);
                    Authentication auth = jwtProvider.getAuthentication(extractUserInfoFromClaims(claims));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.info("Authenticated user with new token: {}", auth.getPrincipal());
                }
            } else if (token != null) {
                if (jwtProvider.validateToken(token)) {
                    Claims claims = jwtProvider.parseClaims(token);
                    if (claims != null) {
                        UserInfo userInfo = extractUserInfoFromClaims(claims);
                        Authentication auth = jwtProvider.getAuthentication(userInfo);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        log.info("Authenticated user: {}", auth.getPrincipal());
                        updateUserInfo(claims);
                    }
                } else {
                    log.warn("Invalid access token");
                }
            }

            log.info("SecurityContextHolder authentication: {}", SecurityContextHolder.getContext().getAuthentication());
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error("Authentication error", exception);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


    // 토큰에서 사용자 정보를 추출하는 메서드 (예시)
    private UserInfo extractUserInfoFromClaims(Claims claims) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(claims.get("sub", String.class));
        String roleString = claims.get("auth", String.class); // "auth" 값을 문자열로 가져옴
        roleString = roleString.replace("ROLE_", ""); // "ROLE_" 접두사를 제거
        Role role = Role.valueOf(roleString); // 역할을 열거형으로 변환
        userInfo.setRoles(Collections.singletonList(role)); // 단일 역할을 리스트 형태로 설정
        // 필요한 사용자 정보를 Claims에서 추출하여 UserInfo 객체에 설정
        return userInfo;
    }
    // 사용자 정보 업데이트 메서드
    private void updateUserInfo(Claims claims) {
        String email = claims.get("email", String.class);

        if (email != null) {
            Optional<Member> optionalMember = memberRepository.findByEmail(email);
            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                // 필요한 필드 업데이트
                member.setName(claims.get("name", String.class));
                member.setRole(claims.get("roles", com.eyy.test.Enumeration.Role.class));
                // 필요한 필드 업데이트 후 저장
                memberRepository.save(member);
                log.info("Member information updated: {}", member);
            }
        }
    }
}