package com.buchi.github.seacher.exception;


public class NotFoundException extends AbstractException {

    public NotFoundException(String code, String message) {
        super(code, message);
    }
    
    public NotFoundException(Integer code, String message) {
        super(code.toString(), message);
    }
}
