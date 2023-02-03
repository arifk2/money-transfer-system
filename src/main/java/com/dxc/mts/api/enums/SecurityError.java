package com.dxc.mts.api.enums;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author mkhan339
 *
 */
public enum SecurityError {

	OPERATION_FAILED(HttpStatus.BAD_REQUEST, 400203, "The current operation is failed."),
	OPERATION_SUCCESS(HttpStatus.OK, 200001, "Your request has been submitted or updated successfully."),
	OPERATION_DELETE(HttpStatus.OK, 200001, "request data deleted successfully.");

	private int code;
	private String description;
	private HttpStatus status;

	SecurityError(HttpStatus status, int code, String description) {
		this.status = status;
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public HttpStatus getHttpStatus() {
		return status;
	}
}