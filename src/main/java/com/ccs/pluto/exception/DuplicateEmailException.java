package com.ccs.pluto.exception;

public class DuplicateEmailException extends RuntimeException {

    String email;

    public DuplicateEmailException(String email) {
        this.email = email;
    }
}
