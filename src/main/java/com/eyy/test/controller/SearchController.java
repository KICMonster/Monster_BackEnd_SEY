package com.eyy.test.controller;

import com.eyy.test.dto.SingleResponseDTO;
import com.eyy.test.dto.TasteStringDTO;
import com.eyy.test.entity.Cocktails;
import com.eyy.test.entity.Member;
import com.eyy.test.repository.MemberRepository;
import com.eyy.test.service.JwtService;
import com.eyy.test.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@CrossOrigin(origins = "https://localhost:5174")
@RequestMapping("/search") // 고유한 경로로 수정
@Controller
public class SearchController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private MemberRepository memberRepository;


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/updateTasteAndRecommend")
    public ResponseEntity<List<Cocktails>> updateTasteAndRecommend(@RequestHeader("Authorization") String jwtToken, @RequestBody TasteStringDTO tasteString) {
        // "Bearer " 접두사 제거
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        }

        // JWT 토큰 유효성 검증
        if (!jwtService.validateToken(jwtToken)) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

    String taste = tasteString.getTasteString();


        System.out.println("taste " + taste);
        System.out.println("jwtToken: " + jwtToken);
        // JWT 토큰에서 멤버 아이디 추출
        String email = jwtService.extractEmailFromToken(jwtToken);

        // 회원 식별
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found for email: " + email));

        // 취향 정보 업데이트
        member.setTaste(tasteString.toString());
        memberRepository.save(member); // 업데이트된 멤버 정보 저장

        // 취향 문자열을 '.'으로 분리하여 리스트로 변환
        List<String> tasteIds = Arrays.asList(tasteString.getTasteString().split("\\."));


        // 칵테일 테이블에서 해당 숫자에 해당하는 칵테일 검색
        List<Cocktails> recommendedCocktails = memberService.findCocktailsByTaste(tasteIds);

        System.out.println("추천 칵테일: " + recommendedCocktails);
        // 응답 데이터 설정
        return ResponseEntity.ok(recommendedCocktails);

    }
}
