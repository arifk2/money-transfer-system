package com.dxc.mts.api.service;

import org.springframework.context.NoSuchMessageException;

import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.model.Bank;

/**
 * 
 * @author manasa
 *
 */
public interface BankService {

	public Bank saveBank(Bank bank);

	public Bank findByBankname(String bankname);

	public Bank getBankById(long id) throws NoSuchMessageException, BankNotFoundException;
	
	}
