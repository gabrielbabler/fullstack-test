package com.fullstack.test.exception.http;

import org.springframework.http.HttpStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class RestException extends RuntimeException {

	private static final long serialVersionUID = -6635290010904547786L;

	public RestException(String message) {
		super(message);
	}

	public abstract HttpStatus getStatus();
	
	/**
	 * If an exception has a response body, it must override this method.
	 * 
	 * @return response body
	 */
	public Object getBody() {
		return null;
	}
}
