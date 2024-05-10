package com.eyy.test.dto;

public class SocialLoginRequest {
    private String accessToken;
    private String service;

    // accessToken 필드의 getter와 setter
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}


