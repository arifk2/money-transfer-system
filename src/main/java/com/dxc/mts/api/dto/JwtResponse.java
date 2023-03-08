package com.dxc.mts.api.dto;

import java.io.Serializable;

/**
 * 
 * @author mkhan339
 *
 */
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 9109287293128062500L;

	private final String jwttoken;
	private String emailAddress;

	public JwtResponse(String jwttoken, String emailAddress) {
		this.jwttoken = jwttoken;
		this.emailAddress = emailAddress;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

}