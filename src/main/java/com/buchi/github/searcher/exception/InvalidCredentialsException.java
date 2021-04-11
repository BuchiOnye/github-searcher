package com.buchi.github.searcher.exception;

public class InvalidCredentialsException extends AbstractException {
    public InvalidCredentialsException(String code, String message) {
        super(code, message);
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}