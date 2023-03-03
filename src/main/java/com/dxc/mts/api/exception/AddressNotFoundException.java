package com.dxc.mts.api.exception;

public class AddressNotFoundException extends Exception {
	private static final long serialVersionUID = 8140630514223432904L;

	private String errorMessage;

	public AddressNotFoundException() {
		super();
	}

	public AddressNotFoundException(String errorMessage) {
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