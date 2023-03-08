package com.dxc.mts.api.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.UserDTO;
import com.dxc.mts.api.exception.UserNotFoundException;

/**
 * 
 * @author mkhan339
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
		UserDTO userDTO = null;
		try {
			userDTO = userService.findByEmailAddress(emailAddress);
			if (userDTO == null) {
				throw new UsernameNotFoundException(
						messageSource.getMessage("mts.user.not.found.message", null, null) + emailAddress);
			}
		} catch (UserNotFoundException e) {
			throw new UsernameNotFoundException(
					messageSource.getMessage("mts.user.not.found.message", null, null) + emailAddress);
		}
		return new User(userDTO.getEmailAddress(), userDTO.getPassword(), new ArrayList<>());
	}
}