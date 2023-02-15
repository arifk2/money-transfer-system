package com.dxc.mts.api.service;

import java.util.List;

import org.springframework.context.NoSuchMessageException;

import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.model.Bank;

public interface BankService {

	public Bank saveBank(Bank bank);

	public Bank findByIfscCode(String ifscCode);

	public Bank getBankById(long id) throws NoSuchMessageException, BankNotFoundException;

	public List<Bank> getBanks();

	public Bank updateBank(Bank bank) throws NoSuchMessageException, BankNotFoundException;

}
