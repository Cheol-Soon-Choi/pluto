package com.ccs.pluto.exception;

import lombok.Getter;

public enum ErrorCode {
    //에러코드 추가
    INVALID_INPUT_VALUE("ERROR_CODE_0001", "Input value error"),

    EMPTY_STOCK("ERROR_CODE_0002", "재고 부족");

    @Getter
    private String code;

    @Getter
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
