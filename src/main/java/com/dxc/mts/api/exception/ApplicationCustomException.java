package com.dxc.mts.api.exception;

/**
 * 
 * @author mkhan339
 *
 */
public class ApplicationCustomException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public ApplicationCustomException() {
		super();
	}

	public ApplicationCustomException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}