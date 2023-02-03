package com.dxc.mts.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.UserRepository;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.User;

/**
 * 
 * @author mkhan339
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptPasswordEncoder;

	@Override
	public User saveUser(User user) {
		User userAlreadyExist = findByEmailAddress(user.getEmailAddress());
		if (user != null && userAlreadyExist != null) {
			return userAlreadyExist;
		} else {
			user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
		}
	}

	@Override
	public User findByEmailAddress(String emailAddress) {
		Optional<User> user = userRepository.findByEmailAddress(emailAddress);
		if (user.isPresent())
			return user.get();
		return null;
	}

	@Override
	public User getUserById(long id) throws NoSuchMessageException, UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException(messageSource.getMessage("mts.user.not.found.message", null, null));
		}
		return user.get();
	}
}
