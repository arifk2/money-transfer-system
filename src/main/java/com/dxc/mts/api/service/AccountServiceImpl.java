package com.dxc.mts.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.AccountRepository;
import com.dxc.mts.api.dto.AccountDTO;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.Account;
import com.dxc.mts.api.model.Bank;
import com.dxc.mts.api.model.User;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserService userService;

	@Autowired
	private BankService bankService;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account saveAccount(AccountDTO accountDTO)
			throws NoSuchMessageException, UserNotFoundException, BankNotFoundException {
		Account existingAccount = getByAccountNumber(accountDTO.getAccountNumber());
		if (existingAccount != null)
			return existingAccount;

		User user = userService.getUserById(accountDTO.getUserId());
		Bank bank = bankService.getBankById(accountDTO.getBankId());

		Account account = new Account();
		account.setAccountNumber(accountDTO.getAccountNumber());
		account.setAccountType(accountDTO.getAccountType());
		account.setAvailableBalance(1000.00);
		account.setBank(bank);
		account.setUser(user);

		return accountRepository.save(account);
	}

	@Override
	public Account getByAccountNumber(Long accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber);
	}

}
