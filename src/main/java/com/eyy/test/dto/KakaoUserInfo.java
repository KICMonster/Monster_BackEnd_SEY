package com.eyy.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfo {
    private Long id; // id 타입을 String에서 Long으로 변경
    private KakaoAccount kakao_account; // kakao_account 객체 추가

    @Getter
    @Setter
    public static class KakaoAccount {
        private Profile profile; // 프로필 정보를 담는 내부 클래스
        private String email; // 이메일 정보 포함

        @Getter
        @Setter
        public static class Profile {
            private String nickname; // 닉네임 정보 포함
        }
    }
}