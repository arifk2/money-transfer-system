package com.dxc.mts.api.service;

import org.springframework.context.NoSuchMessageException;

import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.User;

/**
 * 
 * @author mkhan339
 *
 */
public interface UserService {

	public User saveUser(User user);

	public User findByEmailAddress(String emailAddress);

	public User getUserById(long id) throws NoSuchMessageException, UserNotFoundException;
}