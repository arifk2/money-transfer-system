package com.dxc.mts.api.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.exception.ApplicationCustomException;
import com.dxc.mts.api.model.Account;
import com.dxc.mts.api.service.AccountService;


@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	private MessageSource source;
	
	@Autowired
	AccountService accountservice;
	
	@PostMapping("/add")
	public ResponseEntity<?> saveUser(@RequestBody Account account)
			throws NoSuchMessageException, ApplicationCustomException {
		final Account accountResponse = accountservice.saveAccount(account);
		if (accountResponse != null) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.CREATED.value(),
							source.getMessage("mts.account.success.message", null, null), accountResponse),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
		}
	}

	
}
