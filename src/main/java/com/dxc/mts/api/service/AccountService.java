package com.dxc.mts.api.service;

import org.springframework.context.NoSuchMessageException;

import com.dxc.mts.api.dto.AccountDTO;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.Account;

public interface AccountService {

	public Account saveAccount(AccountDTO accountDTO)
			throws NoSuchMessageException, UserNotFoundException, BankNotFoundException;

	public Account getByAccountNumber(Long accountNumber);

}
