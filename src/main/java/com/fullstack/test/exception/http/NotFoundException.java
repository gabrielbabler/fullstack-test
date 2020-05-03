package com.fullstack.test.exception.http;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RestException{

	private static final long serialVersionUID = 3459413593797522677L;

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
