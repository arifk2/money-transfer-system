package com.dxc.mts.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressRequest {
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String country;
	private Long pinCode;
	
}
