package com.buchi.github.seacher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class ExpectationFailed extends AbstractException{

	private static final long serialVersionUID = 1L;
	
	public ExpectationFailed(String code, String message) {
        super(code, message);
    }
    public ExpectationFailed(Integer code, String message) {
        super(code.toString(), message);
    }
}
