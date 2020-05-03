package com.fullstack.test.exception.http;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;

import com.fullstack.test.response.ErrorResponse;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadRequestException extends RestException {

	private static final long serialVersionUID = 1663698659147040382L;
	
	private Set<ErrorResponse> errorResponseList;
	
	public BadRequestException(ErrorResponse error) {
		this.errorResponseList = Stream.of(error).collect(Collectors.toSet());
	}

	public BadRequestException(Set<ErrorResponse> errorResponseList) {
		this.errorResponseList = errorResponseList;
	}
	
	@Override
	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}
	
	@Override
	public Object getBody() {
		return errorResponseList;
	}
}
