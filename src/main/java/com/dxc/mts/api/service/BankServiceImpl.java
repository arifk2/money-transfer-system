package com.dxc.mts.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.BankRepository;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.model.Bank;

/**
 * 
 * @author manasa
 *
 */
@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private BankService bankService;

	@Override
	public Bank saveBank(Bank bank) {
		// TODO Auto-generated method stub

		Bank bankAlreadyExist = findByBankname(bank.getBankname());
		if (bank != null && bankAlreadyExist != null) {
			return bankAlreadyExist;
		} else

		{
			return bankRepository.save(bank);

		}

	}

	@Override
	public Bank findByBankname(String bankname) {
		// TODO Auto-generated method stub
		Optional<Bank> bank = bankRepository.findByBankname(bankname);
		if (bank.isPresent())
			return bank.get();
		return null;

	}

	@Override
	public Bank getBankById(long id) throws NoSuchMessageException, BankNotFoundException {
		Optional<Bank> bank = bankRepository.findById(id);
		if (bank.isEmpty()) {
			throw new BankNotFoundException(messageSource.getMessage("mts.bank.not.found.message", null, null));
		}
		return bank.get();
	}
	
	

}

		  