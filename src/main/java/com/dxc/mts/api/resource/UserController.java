package com.dxc.mts.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.exception.ApplicationCustomException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.User;
import com.dxc.mts.api.service.UserService;

/**
 * 
 * @author mkhan339
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private MessageSource source;

	@Autowired
	private UserService userService;

	/**
	 * This method is created to add user in the database.
	 * 
	 * @param user holds the user information
	 * @return response entity object
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 */
	@PostMapping("/add")
	public ResponseEntity<?> saveUser(@RequestBody User user)
			throws NoSuchMessageException, ApplicationCustomException {
		final User userResponse = userService.saveUser(user);
		if (userResponse != null) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.CREATED.value(),
							source.getMessage("mts.user.success.message", null, null), userResponse),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This method is created to get the user information based on the user id
	 * 
	 * @param id user id of the user
	 * @return response entity object
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 * @throws UserNotFoundException      user not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable long id)
			throws NoSuchMessageException, ApplicationCustomException, UserNotFoundException {
		final User userResponse = userService.getUserById(id);
		if (userResponse != null) {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(),
					source.getMessage("mts.user.success.message", null, null), userResponse), HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.NOT_FOUND);
		}
	}
}
