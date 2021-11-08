package com.ccs.pluto.exception;

import lombok.Getter;

public enum ErrorCode {
    NOT_NULL("ERROR_CODE_0001", "필수값이 누락되었습니다"),
    NOT_BLANK("ERROR_CODE_0002", "필수값이 누락되었습니다"),
    EMAIL("ERROR_CODE_0003", "이메일 형식이 아닙니다"),
    NOT_EMPTY("ERROR_CODE_0004", "필수값이 누락되었습니다"),
    LENGTH("ERROR_CODE_0005", "비밀번호 자리수가 적절하지 않습니다");

    @Getter
    private String code;

    @Getter
    private String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
