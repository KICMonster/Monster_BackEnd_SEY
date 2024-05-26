package com.monster.luv_cocktail.domain.enumeration;

public enum EmailVerificationResult {
    SUCCESS("인증 성공"),
    FAILURE("인증 실패"),
    CODE_NOT_FOUND("인증 코드를 찾을 수 없음"),
    CODE_MISMATCH("인증 코드 불일치");

    private final String message;

    private EmailVerificationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public static EmailVerificationResult of(boolean authResult) {
        return authResult ? SUCCESS : FAILURE;
    }
}

