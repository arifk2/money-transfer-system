package com.dxc.mts.api.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.AccountRepository;
import com.dxc.mts.api.dao.TransactionRepository;
import com.dxc.mts.api.dto.TransactionDTO;
import com.dxc.mts.api.enums.BaseAppConstants;
import com.dxc.mts.api.exception.AccountNotFoundException;
import com.dxc.mts.api.exception.InvalidAmountException;
import com.dxc.mts.api.model.Account;
import com.dxc.mts.api.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	@Transactional
	public TransactionDTO createTransaction(TransactionDTO transactionDTO)
			throws AccountNotFoundException, NoSuchMessageException, InvalidAmountException {
		Optional<Account> toAccountExist = accountRepository.findByAccountNumber(transactionDTO.getToAccountNumber());
		Optional<Account> fromAccountExist = accountRepository.findByAccountNumber(transactionDTO.getFromAccountNumber());

		Transaction creditTransaction = new Transaction();
		creditTransaction.setTransactionType(transactionDTO.getTransactionType());
		creditTransaction.setTransactionAmount(transactionDTO.getTransactionAmount());
		creditTransaction.setTransactionTimestamp(new Date());
		creditTransaction.setTransactionDate(new Date());
		creditTransaction.setComments(transactionDTO.getComments());
		creditTransaction.setTransferType(BaseAppConstants.CREDIT.getValue());

		if (toAccountExist.isEmpty() || fromAccountExist.isEmpty()) {
			throw new AccountNotFoundException(
					messageSource.getMessage("mts.to.from.account.not.found.message", null, null));
		}
		Account toAccount = toAccountExist.get();
		Account fromAccount = fromAccountExist.get();

		double openingToBalance = toAccount.getAvailableBalance();
		double openingFromBalance = fromAccount.getAvailableBalance();

		if (transactionDTO.getTransactionAmount() == 0 || openingToBalance < transactionDTO.getTransactionAmount()) {
			throw new InvalidAmountException(messageSource.getMessage("mts.tx.invalid.amount", null, null));
		}
		creditTransaction.setOpeningBalance(openingToBalance);

		Transaction debitTransaction = new Transaction();
		debitTransaction.setTransactionType(transactionDTO.getTransactionType());
		debitTransaction.setTransactionAmount(transactionDTO.getTransactionAmount());
		debitTransaction.setTransactionTimestamp(new Date());
		debitTransaction.setTransactionDate(new Date());
		debitTransaction.setOpeningBalance(openingFromBalance);
		debitTransaction.setComments(transactionDTO.getComments());
		debitTransaction.setTransferType(BaseAppConstants.DEBIT.getValue());

		double closingToBalance = openingToBalance + transactionDTO.getTransactionAmount();
		toAccount.setAvailableBalance(closingToBalance);
		creditTransaction.setClosingBalance(closingToBalance);

		double closingFromBalance = openingFromBalance - transactionDTO.getTransactionAmount();
		fromAccount.setAvailableBalance(closingFromBalance);
		debitTransaction.setClosingBalance(closingFromBalance);

		toAccount = accountRepository.save(toAccount);
		fromAccount = accountRepository.save(fromAccount);

		creditTransaction.setAccount(toAccount);
		debitTransaction.setAccount(fromAccount);

		creditTransaction.setUser(toAccount.getUser());
		debitTransaction.setUser(fromAccount.getUser());

		creditTransaction = transactionRepository.save(creditTransaction);
		transactionRepository.save(debitTransaction);

		return new TransactionDTO(creditTransaction, transactionDTO.getFromAccountNumber(),
				BaseAppConstants.SUCCESS.getValue());
	}

}
