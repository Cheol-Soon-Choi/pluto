package com.ccs.pluto.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private List<FieldError> errors;
    private String code;

    private ErrorResponse(final ErrorCode code, String itemName, int stockNumber) {
        this.message = itemName + " " + code.getMessage() + ", 잔여 수량: " + stockNumber + " 개";
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code, String itemName, int stockNumber) {
        return new ErrorResponse(code, itemName, stockNumber);
    }

    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.message = code.getMessage();
        this.errors = errors;
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode code, String email) {
        return new ErrorResponse(code, FieldError.of2(email));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

        private static List<FieldError> of2(String email) {
            List<FieldError> list = new ArrayList<>();
            list.add(new FieldError("email", email, "이미 사용중인 이메일입니다"));
            return list;
        }
    }
}