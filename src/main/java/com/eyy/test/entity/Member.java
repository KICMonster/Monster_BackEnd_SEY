package com.eyy.test.entity;

import com.eyy.test.Enumeration.LoginType;
import com.eyy.test.Enumeration.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;           // 로그인시 id값 역할. 이메일 밸리데이션 체크 필수(방법은 알아서)
    @Column(nullable = true)
    private String password;        // 소셜로그인의 경우가 있으므로 DB에는 null 허용. 기본회원가입시에는 필수
    private String name;            // 카카오의 경우 비즈앱이 아니므로 DTO에서 닉네임으로 반환하여 name으로 저장
    @Column(nullable = true)
    private String birth;            // 생년월일
    private String gender;
    private String phone;           // 전화번호

    @Enumerated(EnumType.STRING)
    private Role role;                 // 소셜로그인 완료 계정,혹은 기본 회원가입 계정 (기본 회원가입계정은 추가 로직 필요.소셜로그인은 토큰생성후 필터 인증시 자동 부여)

    @Enumerated(EnumType.STRING)
    private LoginType loginType;        // 소셜로그인인지 기본회원가입인지 구분하기 위한 enum타입 필드 추가

    private String taste;    // 취향 기능 구현 후 추가 예정. 건너뛰기 기능 만들예정으로 null값 허용
    
    // private Like like;      // 좋아요 기능 구현 후 추가 예정. 내 칵테일리스트(즐겨찾기) 페이지 사용 등

    // JwtToken과의 @OneToMany 관계 설정
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JwtToken> tokens = new ArrayList<>();
    
    // 다른 테이블과 매핑 추가 예정
}