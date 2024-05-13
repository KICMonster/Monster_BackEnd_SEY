package com.eyy.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDTO {
    private String email;
    private String password;
    private String name;
    private String birth;
    private String phone;
    private String gender;

    // Getter, Setter 생략
}
