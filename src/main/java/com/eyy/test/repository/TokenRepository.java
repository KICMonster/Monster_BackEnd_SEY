package com.eyy.test.repository;

import com.eyy.test.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<JwtToken, Long> {
    // 여기에 필요한 추가적인 메서드 정의가 가능합니다.
    // 예시: 특정 사용자의 토큰을 조회하는 메서드
    Optional<JwtToken> findByRefreshToken(String email);
}