package com.spring.vehicle.advice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.spring.vehicle.payload.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.spring.vehicle.exception.AppException;
import com.spring.vehicle.exception.BadRequestException;

import com.spring.vehicle.exception.ResourceNotFoundException;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Autowired
	public ExceptionControllerAdvice(MessageSource messageSource) {
    }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse processValidationError(MethodArgumentNotValidException ex, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		List<ObjectError> allErrors = result.getAllErrors();
		String data = String.join("\n", processAllErrors(allErrors));

		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"Validation Error",
				data,
				resolvePathFromWebRequest(request)
		);
	}

	private List<String> processAllErrors(List<ObjectError> allErrors) {
		return allErrors.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
	}

	private String resolvePathFromWebRequest(WebRequest request) {
		try {
			return ((ServletWebRequest) request).getRequest().getAttribute("javax.servlet.forward.request_uri").toString();
		} catch (Exception ex) {
			return null;
		}
	}


	@ExceptionHandler(value = ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				"Not Found",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleBadRequestException(BadRequestException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"Bad Request",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = MalformedJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorResponse handleMalformedJwtException(MalformedJwtException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.UNAUTHORIZED.value(),
				"Malformed JWT",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = JsonEOFException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ErrorResponse handleJsonEOFException(JsonEOFException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.EXPECTATION_FAILED.value(),
				"JSON EOF Error",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = DeserializationException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ErrorResponse handleDeserializationException(DeserializationException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.EXPECTATION_FAILED.value(),
				"Deserialization Error",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = ExpiredJwtException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ErrorResponse handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.EXPECTATION_FAILED.value(),
				"Expired JWT",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = UnsupportedJwtException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ErrorResponse handleUnsupportedJwtException(UnsupportedJwtException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.EXPECTATION_FAILED.value(),
				"Unsupported JWT",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"Illegal Argument",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = SignatureException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ErrorResponse handleSignatureException(SignatureException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.EXPECTATION_FAILED.value(),
				"Signature Error",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = SecurityException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ErrorResponse handleSecurityException(SecurityException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.EXPECTATION_FAILED.value(),
				"Security Error",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = JwtException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ErrorResponse handleJwtException(JwtException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.EXPECTATION_FAILED.value(),
				"JWT Error",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}

	@ExceptionHandler(value = AppException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleAppException(AppException ex, WebRequest request) {
		return new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error",
				ex.getMessage(),
				resolvePathFromWebRequest(request)
		);
	}
}
