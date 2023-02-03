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

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}