package com.dxc.mts.api.exception;

/**
 * 
 * @author mkhan339
 *
 */
public class TransactionFailedException extends Exception {
	private static final long serialVersionUID = 8140630514223432904L;

	private String errorMessage;

	public TransactionFailedException() {
		super();
	}

	public TransactionFailedException(String errorMessage) {
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