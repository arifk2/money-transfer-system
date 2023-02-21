package com.dxc.mts.api.service;

import org.springframework.context.NoSuchMessageException;

import com.dxc.mts.api.dto.TransactionDTO;
import com.dxc.mts.api.exception.AccountNotFoundException;
import com.dxc.mts.api.exception.InvalidAmountException;

public interface TransactionService {

	public TransactionDTO createTransaction(TransactionDTO transactionDTO)
			throws AccountNotFoundException, NoSuchMessageException, InvalidAmountException;

}
