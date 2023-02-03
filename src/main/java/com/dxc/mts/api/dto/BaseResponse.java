package com.dxc.mts.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author mkhan339
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

	private Object data;
	@JsonInclude(Include.NON_NULL)
	private Integer errorCode;
	@JsonInclude(Include.NON_NULL)
	private String errorMessage;
	private String message;
	private int status;

	public BaseResponse(int status, Integer errorCode, String errorMessage) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public BaseResponse(int status, String message) {
		this.message = message;
		this.status = status;
	}

	public BaseResponse(int status, String message, Object data) {
		this.data = data;
		this.message = message;
		this.status = status;
	}
}