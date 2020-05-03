package com.fullstack.test.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fullstack.test.config.MessageConfig;
import com.fullstack.test.exception.http.RestException;
import com.fullstack.test.response.ErrorResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {
	
	private final MessageConfig messageConfig;

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Error> mediaTypeNotFoundException(final HttpMediaTypeNotSupportedException e) {
		return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<List<ErrorResponse>> assertionException(final HttpMessageNotReadableException e) {
		if (e.getCause() instanceof JsonMappingException) {
			JsonMappingException cause = (JsonMappingException) e.getCause();
			String field = cause.getPathReference().split("\\[\"")[1].replace("\"]", "");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Arrays.asList(ErrorResponse.builder()
							.code("400.001")
							.message(messageConfig.getMessage("400.001", field))
							.build()));
		}
		return defaultBadRequestError();
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<List<ErrorResponse>> missingServletRequestParameterException(
			final MissingServletRequestParameterException e) {
		return defaultBadRequestError();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErrorResponse>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException methodArgumentNotValidException) {
		List<ErrorResponse> messageErrors = Optional.ofNullable(methodArgumentNotValidException)
				.filter(argumentNotValidException -> !ObjectUtils.isEmpty(argumentNotValidException.getBindingResult()))
				.map(MethodArgumentNotValidException::getBindingResult)
				.filter(bindingResult -> !ObjectUtils.isEmpty(bindingResult.getAllErrors()))
				.map(BindingResult::getAllErrors)
				.map(Stream::of)
				.orElseGet(Stream::empty)
				.flatMap(Collection::stream)
				.filter(objectError -> !ObjectUtils.isEmpty(objectError))
				.map(this::createError)
				.collect(Collectors.toList());
		return new ResponseEntity<>(messageErrors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RestException.class)
	public ResponseEntity<Object> handleRestException(RestException restException) {
		log.error(restException.getMessage(), restException);
		return ResponseEntity.status(restException.getStatus()).body(restException.getBody());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception exception) {
		log.error(exception.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	private ResponseEntity<List<ErrorResponse>> defaultBadRequestError() {
		return new ResponseEntity<>(
				Arrays.asList(ErrorResponse.builder()
						.code("400.000")
						.message(messageConfig.getMessage("400.000"))
						.build()),
				HttpStatus.BAD_REQUEST);
	}
	
	private ErrorResponse createError(ObjectError error) {
		String field = "";
		if (error instanceof FieldError) {
			field = ((FieldError) error).getField();
		}
		return ErrorResponse.builder()
				.code(error.getDefaultMessage())
				.message(messageConfig.getMessage(error.getDefaultMessage(), field))
				.build();
	}

	
}