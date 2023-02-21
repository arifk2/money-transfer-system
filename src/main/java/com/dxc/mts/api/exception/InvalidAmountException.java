package com.dxc.mts.api.exception;

public class InvalidAmountException extends Exception {

	private static final long serialVersionUID = 6124467379681573354L;

	private String errorMessage;

	public InvalidAmountException() {
		super();
	}

	public InvalidAmountException(String errorMessage) {
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
