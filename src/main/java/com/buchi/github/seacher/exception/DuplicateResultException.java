package com.buchi.github.seacher.exception;

public class DuplicateResultException extends AbstractException{

	private static final long serialVersionUID = 1L;

	public DuplicateResultException(String code, String message){
        super(code,message);
    }

}
