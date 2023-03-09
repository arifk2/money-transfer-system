package com.dxc.mts.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.AccountRepository;
import com.dxc.mts.api.model.Account;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AccountRepository accRepository;

	@Override
	public Account saveAccount(Account acc) {
		Account accAlreadyExist = findByAccountId(acc.getAccountId());
		if (acc != null && accAlreadyExist != null) {
			return accAlreadyExist;
		} else {
			
			return accRepository.save(acc);
		}
	}

	private Account findByAccountId(Long accountId) {
		Optional<Account> account = accRepository.findByAccountId(accountId);
		if (account.isPresent())
			return account.get();
		return null;
	}
	}


