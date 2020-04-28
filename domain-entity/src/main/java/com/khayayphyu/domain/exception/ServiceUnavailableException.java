package com.khayayphyu.domain.exception;

public class ServiceUnavailableException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceUnavailableException() {
		super();
	}

	public ServiceUnavailableException(String message) {
		super(message);
	}

	public ServiceUnavailableException(Exception cause) {
		super(cause);
	}

	public ServiceUnavailableException(String message, Exception cause) {
		super(message, cause);
	}
}
