package com.ccs.pluto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    //에러코드(enum)의 종류가 늘어나면 발생하는 에러에 따라 ErrorCode를 변경할 수 있도록 메서드 변경해야 함.

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

       final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfStockException.class)
    protected ResponseEntity<ErrorResponse> handleOutOfStockException(OutOfStockException e) {

        final ErrorResponse response = ErrorResponse.of(ErrorCode.EMPTY_STOCK);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
