package com.eyy.test.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAuthCodeStore {

    private static final Map<String, String> authCodes = new ConcurrentHashMap<>();

    public void saveCode(String email, String code) {
        authCodes.put(email, code);
    }

    public String getCode(String email) {
        return authCodes.get(email);
    }

    public boolean verifyCode(String email, String code) {
        return code.equals(authCodes.get(email));
    }
}
