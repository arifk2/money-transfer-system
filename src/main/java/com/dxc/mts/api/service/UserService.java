package com.dxc.mts.api.service;

import java.util.List;

import org.springframework.context.NoSuchMessageException;

import com.dxc.mts.api.dao.UserDTO;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.User;

/**
 * 
 * @author mkhan339
 *
 */
public interface UserService {

	public User saveUser(User user) throws NoSuchMessageException;

	public UserDTO findByEmailAddress(String emailAddress) throws NoSuchMessageException, UserNotFoundException;

	public User getUserById(long id) throws NoSuchMessageException, UserNotFoundException;

	public List<User> getUsers();

	public User updateUser(User user) throws NoSuchMessageException, UserNotFoundException;
}
