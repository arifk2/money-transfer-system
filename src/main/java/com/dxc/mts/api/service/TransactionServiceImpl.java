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
		Optional<Account> toAccountExist = accountRepository.findById(transactionDTO.getToAccountId());
		Optional<Account> fromAccountExist = accountRepository.findById(transactionDTO.getFromAccountId());

		Transaction debitTransaction = new Transaction();
		debitTransaction.setTransactionType(transactionDTO.getTransactionType());
		debitTransaction.setTransactionAmount(transactionDTO.getTransactionAmount());
		debitTransaction.setTransactionTimestamp(new Date());
		debitTransaction.setTransactionDate(new Date());
		debitTransaction.setComments(transactionDTO.getComments());
		debitTransaction.setTransferType(BaseAppConstants.DEBIT.getValue());

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
		debitTransaction.setOpeningBalance(openingToBalance);

		Transaction creditTransaction = new Transaction();
		creditTransaction.setTransactionType(transactionDTO.getTransactionType());
		creditTransaction.setTransactionAmount(transactionDTO.getTransactionAmount());
		creditTransaction.setTransactionTimestamp(new Date());
		creditTransaction.setTransactionDate(new Date());
		creditTransaction.setOpeningBalance(openingFromBalance);
		creditTransaction.setComments(transactionDTO.getComments());
		creditTransaction.setTransferType(BaseAppConstants.CREDIT.getValue());

		double closingToBalance = openingToBalance - transactionDTO.getTransactionAmount();
		toAccount.setAvailableBalance(closingToBalance);
		debitTransaction.setClosingBalance(closingToBalance);

		double closingFromBalance = openingFromBalance + transactionDTO.getTransactionAmount();
		fromAccount.setAvailableBalance(closingFromBalance);
		creditTransaction.setClosingBalance(closingFromBalance);

		toAccount = accountRepository.save(toAccount);
		fromAccount = accountRepository.save(fromAccount);

		debitTransaction.setAccount(toAccount);
		creditTransaction.setAccount(fromAccount);

		debitTransaction.setUser(toAccount.getUser());
		creditTransaction.setUser(fromAccount.getUser());

		debitTransaction = transactionRepository.save(debitTransaction);
		transactionRepository.save(creditTransaction);

		return new TransactionDTO(debitTransaction, transactionDTO.getFromAccountId(),
				BaseAppConstants.SUCCESS.getValue());
	}

}
