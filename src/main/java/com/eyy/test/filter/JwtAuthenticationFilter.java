package com.eyy.test.filter;

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
            String token = parseBearerToken(request);

            if (token != null && !jwtProvider.validateToken(token)) {
                // 액세스 토큰 만료 시 리프레시 토큰 유효성 검사
                String refreshToken = request.getHeader("Refresh");
                if (refreshToken != null && jwtProvider.validateRefreshToken(refreshToken)) {
                    Claims claims = jwtProvider.parseClaims(refreshToken);
                    String username = claims.getSubject();

                    // 새로운 액세스 토큰 생성
                    String newAccessToken = jwtProvider.createAccessToken(username, claims.get("auth", String.class));

                    // 새로운 액세스 토큰을 응답 헤더에 추가
                    response.setHeader("Authorization", "Bearer " + newAccessToken);

                    // 새로운 액세스 토큰으로 Authentication 객체 생성 및 SecurityContextHolder에 설정
                    Authentication auth = jwtProvider.getAuthentication(extractUserInfoFromClaims(claims));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } else if (token != null) {
                // 기존 로직 실행
                Claims claims = jwtProvider.parseClaims(token);
                if (claims != null) {
                    UserInfo userInfo = extractUserInfoFromClaims(claims);
                    Authentication auth = jwtProvider.getAuthentication(userInfo);
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    // 사용자 정보 업데이트
                    updateUserInfo(claims);

                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error("Authentication error", exception);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    // 토큰에서 사용자 정보를 추출하는 메서드 (예시)
    private UserInfo extractUserInfoFromClaims(Claims claims) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(claims.get("email", String.class));
        userInfo.setRoles(Arrays.asList(claims.get("roles", com.eyy.test.Enumeration.Role.class)));
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