package com.greenhouse.exception;

public class NotFoundInDatabaseGreenhouseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9093638769196318207L;

	public NotFoundInDatabaseGreenhouseException(String msg) {
		super(msg);
	}
	
}
