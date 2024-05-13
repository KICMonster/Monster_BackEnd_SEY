package com.eyy.test.controller;

import com.eyy.test.Enumeration.EmailVerificationResult;
import com.eyy.test.Enumeration.ExceptionCode;
import com.eyy.test.dto.EmailResponseDTO;
import com.eyy.test.dto.JoinRequestDTO;
import com.eyy.test.dto.JwtTokenDTO;
import com.eyy.test.dto.SingleResponseDTO;
import com.eyy.test.entity.Member;
import com.eyy.test.exception.BusinessLogicException;
import com.eyy.test.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:5174")
@RequestMapping("/join") // 고유한 경로로 수정
@Controller
public class JoinController {

    private final MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/emails/verification-requests")
    public ResponseEntity<Void> sendMessage(@Valid @RequestBody EmailResponseDTO request) {
        String email = request.getEmail();
        System.out.println(email);
        try {
            memberService.sendCodeToEmail(email);
        } catch (BusinessLogicException e) {
            if (e.getExceptionCode() == ExceptionCode.MEMBER_EXISTS) {
                return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict 상태 코드 반환
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/emails/verifications")
    public ResponseEntity<SingleResponseDTO<EmailVerificationResult>> verificationEmail(@RequestParam("email") @Valid String email,
                                                                                        @RequestParam("code") String authCode) {
        EmailVerificationResult response = memberService.verifyCode(email, authCode);

        SingleResponseDTO<EmailVerificationResult> responseDto = new SingleResponseDTO<>(response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitJoinRequest(@RequestBody JoinRequestDTO requestDto) {
        try {
            memberService.processJoinRequest(requestDto);
            return ResponseEntity.ok("Join request processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process join request");
        }
    }
    // JWT 토큰을 받아서 회원 탈퇴 처리 요청을 처리하는 메서드 - 마이페이지 구축되지않았으므로 프론트 구축후 연결
    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdrawMember(@RequestBody JwtTokenDTO jwtToken) {
        try {
            // JWT 토큰에서 회원 정보를 추출하여 해당 회원을 찾아서 탈퇴 처리
            Member member = memberService.findMemberByJwtToken(jwtToken.getJwtAccessToken());
            System.out.println(member.getEmail());
            memberService.processWithdrawal(member.getEmail());
            return ResponseEntity.ok("Member withdrawal processed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process member withdrawal");
        }
    }
}