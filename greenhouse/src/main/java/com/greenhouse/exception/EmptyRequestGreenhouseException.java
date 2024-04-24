package com.greenhouse.exception;

public class EmptyRequestGreenhouseException extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9028792009909516516L;

	public EmptyRequestGreenhouseException(String msg) {
		super(msg);
	}
}
