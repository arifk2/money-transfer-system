package com.dxc.mts.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.dto.JwtRequest;
import com.dxc.mts.api.dto.JwtResponse;
import com.dxc.mts.api.service.JwtUserDetailsService;
import com.dxc.mts.api.util.JwtTokenUtil;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 * @author mkhan339
 *
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * This method is created generate from authenticated token
	 * 
	 * @param authenticationRequest holds information of the JwtRequest
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "Api to authenticate the user based on username and password")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
		try {
			authenticate(authenticationRequest.getEmailAddress(), authenticationRequest.getPassword());

			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.getEmailAddress());
			final String token = jwtTokenUtil.generateToken(userDetails);
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.OK.value(), messageSource.getMessage("mts.success.message", null, null),
							new JwtResponse(token, userDetails.getUsername())),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.UNAUTHORIZED.value(),
							messageSource.getMessage("mts.security.invalid.credentials", null, null), null),
					HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * This method is created to authenticate the user
	 * 
	 * @param username holds the information of the username
	 * @param password holds the information of the password
	 * @throws Exception
	 */
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception(messageSource.getMessage("mts.security.user.disabled", null, null), e);
		} catch (BadCredentialsException e) {
			throw new Exception(messageSource.getMessage("mts.security.invalid.credentials", null, null), e);
		}
	}
}
