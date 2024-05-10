package com.eyy.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenDTO {
    private String grantType;
    private String jwtAccessToken;
    private String refreshToken;
    private LocalDate expiresIn;
}
