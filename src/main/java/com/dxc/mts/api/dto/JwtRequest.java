package com.dxc.mts.api.dto;

import java.io.Serializable;

/**
 * 
 * @author mkhan339
 *
 */
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = -8111550775276415409L;

	private String emailAddress;
	private String password;

	public JwtRequest() {

	}

	public JwtRequest(String emailAddress, String password) {
		this.emailAddress = emailAddress;
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}