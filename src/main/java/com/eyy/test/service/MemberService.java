package com.eyy.test.service;

import com.eyy.test.Enumeration.EmailVerificationResult;
import com.eyy.test.Enumeration.ExceptionCode;
import com.eyy.test.Enumeration.LoginType;
import com.eyy.test.Enumeration.Role;
import com.eyy.test.dto.JoinRequestDTO;
import com.eyy.test.entity.Member;
import com.eyy.test.exception.BusinessLogicException;
import com.eyy.test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    private final MemberRepository memberRepository;

    private final SendEmailService mailService;

    private final InMemoryAuthCodeStore inMemoryAuthCodeStore;

    @Value("${spring.mail.properties.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    public void sendCodeToEmail(String toEmail) {
        this.checkDuplicatedEmail(toEmail);
        String title = "Travel with me 이메일 인증 번호";
        String authCode = this.createCode();
        mailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 메모리 내 저장소에 저장
        inMemoryAuthCodeStore.saveCode(AUTH_CODE_PREFIX + toEmail, authCode);
        // Note: authCodeExpirationMillis 는 ConcurrentHashMap에서 직접적으로 지원되지 않는 기능입니다.
        // 만료 로직을 구현하려면 별도의 메커니즘을 고려해야 합니다.
    }

    private void checkDuplicatedEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }


    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new BusinessLogicException(ExceptionCode.NO_SUCH_ALGORITHM);
        }
    }

    public EmailVerificationResult verifyCode(String email, String authCode) {
        String key = AUTH_CODE_PREFIX + email;
        String storedAuthCode = inMemoryAuthCodeStore.getCode(key);
        if (storedAuthCode == null) {
            return EmailVerificationResult.CODE_NOT_FOUND;
        }
        boolean authResult = storedAuthCode.equals(authCode);
        return EmailVerificationResult.of(authResult);
    }

    @Transactional
    public void processJoinRequest(JoinRequestDTO requestDto) {
        Member member = new Member();
        member.setEmail(requestDto.getEmail());
        member.setPassword(requestDto.getPassword()); // 일반 회원가입의 경우에만 사용
        member.setName(requestDto.getName());
        member.setBirth(requestDto.getBirth());
        member.setPhone(requestDto.getPhone());
        member.setGender(requestDto.getGender());

        // 기본 회원가입인 경우에만 설정
        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            member.setRole(Role.USER); // 기본 회원가입은 USER 권한 부여
            member.setLoginType(LoginType.N); // 기본 회원가입인 경우 로그인 가능한 계정
        }

        memberRepository.save(member);
    }
}
