package com.dxc.mts.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.dto.TransactionDTO;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.exception.AccountNotFoundException;
import com.dxc.mts.api.exception.InvalidAmountException;
import com.dxc.mts.api.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private MessageSource source;

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/debit")
	@Operation(summary = "Api to create new transaction")
	public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transactionDTO) {
		TransactionDTO txResponse = null;
		try {
			txResponse = transactionService.createTransaction(transactionDTO);
			if (txResponse != null) {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.CREATED.value(),
						source.getMessage("mts.success.message", null, null), txResponse), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
						SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
			}
		} catch (AccountNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.to.from.account.not.found.message", null, null)),
					HttpStatus.NOT_FOUND);
		} catch (InvalidAmountException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.tx.invalid.amount", null, null)),
					HttpStatus.NOT_FOUND);
		}

	}
}
