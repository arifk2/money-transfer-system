package com.dxc.mts.api.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.TransactionRepository;
import com.dxc.mts.api.dto.StatementDTO;
import com.dxc.mts.api.exception.StatementNotFoundException;
import com.dxc.mts.api.model.Transaction;

@Service
public class StatementServiceImpl implements StatementService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public List<StatementDTO> getTopTenTransaction(Long userId)
			throws NoSuchMessageException, StatementNotFoundException {
		List<Transaction> topTenTransaction = transactionRepository.findtop10TxByTransactionDate(userId);
		if (topTenTransaction == null || topTenTransaction.isEmpty()) {
			throw new StatementNotFoundException(messageSource.getMessage("mts.tx.not.found.message", null, null));
		}
		List<StatementDTO> listStatementDTOs = new LinkedList<>();
		for (Transaction transaction : topTenTransaction) {
			listStatementDTOs.add(new StatementDTO(transaction));
		}
		return listStatementDTOs;
	}

	@Override
	public List<StatementDTO> getCurrentMonthTransaction(Date transactionDate) {
		return null;
	}

	@Override
	public List<StatementDTO> getTransactionByDateRange(Date toDate, Date fromDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatementDTO> getLastTransaction(Date transactionDate, int month) {
		// TODO Auto-generated method stub
		return null;
	}

}
