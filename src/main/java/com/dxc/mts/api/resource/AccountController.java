package com.dxc.mts.api.resource;

import java.util.List;

import javax.validation.Valid;

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

import com.dxc.mts.api.dto.AccountDTO;
import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.exception.AccountNotFoundException;
import com.dxc.mts.api.exception.ApplicationCustomException;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.Account;
import com.dxc.mts.api.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private MessageSource source;

	@Autowired
	private AccountService accountService;

	/**
	 * This method is created to add account
	 * 
	 * @param accountDTO holds the information of the accountDTO
	 * @return
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 */
	@PostMapping("/add")
	public ResponseEntity<?> saveAccount(@Valid @RequestBody AccountDTO accountDTO)
			throws NoSuchMessageException, ApplicationCustomException {
		Account accountResponse = null;
		try {
			accountResponse = accountService.saveAccount(accountDTO);
			if (accountResponse != null) {
				return new ResponseEntity<Object>(
						new BaseResponse(HttpStatus.CREATED.value(),
								source.getMessage("mts.success.message", null, null), accountResponse),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
						SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.user.not.found.message", null, null)),
					HttpStatus.NOT_FOUND);
		} catch (BankNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.bank.not.found.message", null, null)),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method is created to get list of accounts
	 * 
	 * @return response entity object
	 */
	@GetMapping
	public ResponseEntity<?> getAccounts() {
		final List<AccountDTO> accountResponse = accountService.getAccounts();
		if (accountResponse != null) {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(),
					source.getMessage("mts.success.message", null, null), accountResponse), HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method is created to get the account information based on the account id
	 * 
	 * @param id account id of the of the account
	 * @return response entity object
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getAccount(@PathVariable long id)
			throws NoSuchMessageException, ApplicationCustomException {
		AccountDTO accountDTO = null;
		try {
			accountDTO = accountService.getAccountById(id);
			if (accountDTO != null) {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(),
						source.getMessage("mts.success.message", null, null), accountDTO), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(),
						SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.NOT_FOUND);
			}
		} catch (AccountNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.account.not.found.message", null, null)),
					HttpStatus.NOT_FOUND);
		}
	}
}
