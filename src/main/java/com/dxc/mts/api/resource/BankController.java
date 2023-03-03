package com.dxc.mts.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.exception.ApplicationCustomException;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.Bank;
import com.dxc.mts.api.service.BankService;

@RestController
@RequestMapping("/banks")
public class BankController {
	/**
	 * 
	 * @author manasa
	 *
	 */

	@Autowired
	private MessageSource source;

	@Autowired
	private BankService bankService;

	/**
	 * This method is created to add bank in the database.
	 * 
	 * @param bank holds the bank information
	 * @return response entity object
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 */
	@PostMapping("/add")
	public ResponseEntity<?> saveBank(@RequestBody Bank bank)
			throws NoSuchMessageException, ApplicationCustomException {
		final Bank bankResponse = bankService.saveBank(bank);
		if (bankResponse != null) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.CREATED.value(),
							source.getMessage("mts.bank.success.message", null, null), bankResponse),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This method is created to get the bank information based on the bank name
	 * 
	 * @param id bank id of the bank
	 * @return response entity object
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getBank(@PathVariable long id) throws NoSuchMessageException, ApplicationCustomException {
		Bank bankResponse = null;
		try {
			bankResponse = bankService.getBankById(id);
			if (bankResponse != null) {
				return new ResponseEntity<Object>(
						new BaseResponse(HttpStatus.OK.value(),
								source.getMessage("mts.bank.success.message", null, null), bankResponse),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(),
						SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.NOT_FOUND);
			}
		} catch (BankNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(),
							source.getMessage("mts.bank.not.found.message", null, null), bankResponse),
					HttpStatus.NOT_FOUND);
		}

	}
	

	 }


