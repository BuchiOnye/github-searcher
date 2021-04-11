package com.buchi.github.searcher.exception;


public class BadRequestException extends AbstractException{

    public BadRequestException(String code, String message){
        super(code,message);
    }
    
    public BadRequestException(Integer code, String message){
        super(code.toString(), message);
    }
}