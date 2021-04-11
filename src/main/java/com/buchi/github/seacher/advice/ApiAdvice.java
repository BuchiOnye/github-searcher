package com.buchi.github.seacher.advice;

import com.buchi.github.seacher.exception.BadRequestException;
import com.buchi.github.seacher.exception.DuplicateResultException;
import com.buchi.github.seacher.exception.InvalidCredentialsException;
import com.buchi.github.seacher.exception.NotFoundException;
import com.buchi.github.seacher.model.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
public class ApiAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new ConcurrentHashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Response<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
		Response<?> response = new Response<>();
		response.setCode(HttpStatus.BAD_REQUEST.toString().toString());
		response.setMessage(exception.getLocalizedMessage());
		if (exception.getCause() != null) {
			String message = exception.getCause().getMessage();
			if (exception.getCause() instanceof JsonMappingException) {
				String[] arr = message.split("\\(");
				if (arr.length > 0) {
					String temp = arr[0];
					String[] arr2 = message.split("\\[");
					if (arr2.length > 1) {
						message = temp + " (fieldName: [" + arr2[1];
					} else {
						message = temp;
					}
				}
			}

			if (exception.getCause() instanceof JsonParseException) {
				String[] arr = message.split("at");
				if (arr.length > 0) {
					String temp = arr[0];
					JsonParseException jpe = (JsonParseException) exception.getCause();
					message = temp + " [line: " + jpe.getLocation().getLineNr() + ", column: " + jpe.getLocation().getColumnNr() + "]";
				}
			}
			response.setMessage(message);
		}
		return response;
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Response<?> handleBadRequestException(BadRequestException e) {
		Response<?> response = new Response<>();
		response.setCode("400");
		response.setMessage(e.getMessage());
		return response;
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public Response<?> handleNotFoundException(NotFoundException e) {
		Response<?> response = new Response<>();
		response.setCode(HttpStatus.NOT_FOUND.toString());
		response.setMessage(e.getMessage());
		return response;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Response<?> handleIllegalArgumentException(IllegalArgumentException exception) {
		Response<?> response = new Response<>();
		response.setCode("400");
		response.setMessage(exception.getMessage());
		return response;
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response<?> handleInvalidCredentialsException(InvalidCredentialsException exception) {
		Response<?> response = new Response<>();
        response.setCode(HttpStatus.UNAUTHORIZED.toString());
        response.setMessage(exception.getMessage());
        return response;
    }
	
	@ExceptionHandler(DuplicateResultException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public Response<?> handDuplicateResultException(DuplicateResultException exception) {
		Response<?> response = new Response<>();
        response.setCode(HttpStatus.EXPECTATION_FAILED.toString());
        response.setMessage(exception.getMessage());
        return response;
    }
}
