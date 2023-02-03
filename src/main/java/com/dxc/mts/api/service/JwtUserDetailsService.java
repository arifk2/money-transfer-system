package com.dxc.mts.api.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
		com.dxc.mts.api.model.User user = userService.findByEmailAddress(emailAddress);
		if (user == null) {
			throw new UsernameNotFoundException(
					messageSource.getMessage("mts.user.not.found.message", null, null) + emailAddress);
		}
		return new User(user.getEmailAddress(), user.getPassword(), new ArrayList<>());
	}
}