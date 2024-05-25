package com.monster.luvCocktail.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "TOKEN_SEQ_GENERATOR", sequenceName = "TOKEN_SEQ", allocationSize = 1)
public class JwtToken {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TOKEN_SEQ_GENERATOR")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId; // 소문자로 변경: Java 카멜 케이스 규칙을 따름

    private String grantType;
    private String jwtAccessToken;
    private String refreshToken;
    private LocalDate expiresIn;    // 리프레시토큰 만료일자. 액세스토큰 만료시 리프레시토큰으로 액세스토큰 재발급하므로 필요

    @ManyToOne // Member와의 ManyToOne 관계를 나타냄
    @JoinColumn(name = "member_id") // 외래 키의 이름 지정
    private Member member;

}