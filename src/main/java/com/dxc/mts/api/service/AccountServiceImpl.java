package com.dxc.mts.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.AccountRepository;
import com.dxc.mts.api.dto.AccountDTO;
import com.dxc.mts.api.exception.AccountNotFoundException;
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

	@Autowired
	private MessageSource messageSource;

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

	@Override
	public List<AccountDTO> getAccounts() {
		List<AccountDTO> accountsDtoList = new ArrayList<>();
		List<Account> accounts = accountRepository.findAll();

		if (accounts != null && accounts.size() != 0) {
			for (Account account : accounts) {
				accountsDtoList.add(new AccountDTO(account));
			}
		}
		return accountsDtoList;
	}

	@Override
	public AccountDTO getAccountById(long id) throws NoSuchMessageException, AccountNotFoundException {
		Optional<Account> accounts = accountRepository.findByAccountId(id);
		if (accounts.isEmpty()) {
			throw new AccountNotFoundException(messageSource.getMessage("mts.account.not.found.message", null, null));
		} else {
			return new AccountDTO(accounts.get());
		}
	}

	@Override
	public List<AccountDTO> getAccountByUserId(long id) throws NoSuchMessageException, AccountNotFoundException {
		List<Account> accounts = accountRepository.findByAccountUserId(id);
		List<AccountDTO> accountsDtoList = new ArrayList<>();
		if (accounts.isEmpty()) {
			throw new AccountNotFoundException(messageSource.getMessage("mts.account.not.found.message", null, null));
		} else {
			if (accounts != null && accounts.size() != 0) {
				for (Account account : accounts) {
					accountsDtoList.add(new AccountDTO(account));
				}
			}
			return accountsDtoList;
		}
	}

	@Override
	public List<AccountDTO> getAccountByBankId(long id) throws NoSuchMessageException, AccountNotFoundException {
		List<Account> accounts = accountRepository.findByAccountBankId(id);
		List<AccountDTO> accountsDtoList = new ArrayList<>();
		if (accounts.isEmpty()) {
			throw new AccountNotFoundException(messageSource.getMessage("mts.account.not.found.message", null, null));
		} else {
			if (accounts != null && accounts.size() != 0) {
				for (Account account : accounts) {
					accountsDtoList.add(new AccountDTO(account));
				}
			}
			return accountsDtoList;
		}

	}

}
