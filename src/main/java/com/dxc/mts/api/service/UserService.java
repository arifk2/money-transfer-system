package com.dxc.mts.api.service;

import java.util.List;

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

	public List<User> getUsers();

	public User updateUser(User user) throws NoSuchMessageException, UserNotFoundException;
}
