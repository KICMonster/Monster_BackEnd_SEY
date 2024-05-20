package com.monster.luvCocktails.domain.exception;

import com.monster.luvCocktails.domain.Enumeration.ExceptionCode;
import lombok.Getter;

public class BusinessLogicException extends RuntimeException {
    @Getter
    private ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
