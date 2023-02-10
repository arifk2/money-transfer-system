package com.dxc.mts.api.enums;

/**
 * 
 * @author mkhan339
 *
 */
public enum BaseAppConstants {

	MM_DD_YYYY_HH_MM_SS_WITHZONE("MM-dd-yyyy'T'HH:mm:ss'Z'"), 
	TIMEZONE_UTC("UTC"), 
	MM_DD_YYYY("MM-dd-yyyy"),
	INVALID_DATE_FORMAT_MESSAGE("Requested date format is not valid, and the valid date format is MM-dd-yyyy'T'HH:mm:ss'Z'. If date is not available use 00-00-1971T00:00:00Z."),
	BACK_SLASH("/"), 
	BLANK(""),
	NOT_AVAILABLE("N/A"), 
	SAVINGS("SAVINGS"), 
	CURRENT("CURRENT"),
	AUTHORIZATION("Authorization"), 
	BEARER("Bearer ");

	private String value;

	BaseAppConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}