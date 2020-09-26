package com.misa.config;

public class CustomerNotFound extends RuntimeException {

	public CustomerNotFound (String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerNotFound (String message) {
		super(message);
	}
	
	public CustomerNotFound (Throwable cause) {
		super(cause);
	}
	
	public CustomerNotFound (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
