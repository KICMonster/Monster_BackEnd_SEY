package com.eyy.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverUserInfo {
    private NResponse response; // 'response' 필드에 해당하는 클래스
    @Getter
    @Setter
    public static class NResponse {
        private String id;
        private String email;
        private String name;
        // 기타 필드...
    }
}

