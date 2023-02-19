package com.dxc.mts.api.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.BankRepository;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.model.Bank;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private MessageSource messageSource;

	@Transactional
	@Override
	public Bank saveBank(Bank bank) {
		Bank bankAlreadyExist = findByIfscCode(bank.getIfscCode());
		if (bank != null && bankAlreadyExist != null) {
			return bankAlreadyExist;
		} else {
			return bankRepository.save(bank);
		}
	}

	@Override
	public Bank findByIfscCode(String ifscCode) {
		if (ifscCode != null) {
			Optional<Bank> bank = bankRepository.findByIfscCode(ifscCode);
			if (bank.isPresent())
				return bank.get();
		}
		return null;
	}

	@Override
	public Bank getBankById(long id) throws NoSuchMessageException, BankNotFoundException {
		Optional<Bank> user = bankRepository.findById(id);
		if (user.isEmpty()) {
			throw new BankNotFoundException(messageSource.getMessage("mts.bank.not.found.message", null, null));
		}
		return user.get();
	}

	@Override
	public List<Bank> getBanks() {
		return bankRepository.findAll();
	}

	@Override
	public Bank updateBank(Bank bank) throws NoSuchMessageException, BankNotFoundException {
		Bank bankFound = getBankById(bank.getBankId());

		bankFound.setBankname(bank.getBankname());
		bankFound.setBranch(bank.getBranch());
		bankFound.setIfscCode(bank.getIfscCode());

		return bankRepository.save(bankFound);
	}

}
