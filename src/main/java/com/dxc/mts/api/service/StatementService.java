package com.dxc.mts.api.service;

import java.util.Date;
import java.util.List;

import com.dxc.mts.api.dto.StatementDTO;
import com.dxc.mts.api.exception.StatementNotFoundException;

/*
 * 
 * Last 1 or 3 month Transactions
Between date range
 * 
 * */
public interface StatementService {

	public List<StatementDTO> getTopTenTransaction(Long userId) throws StatementNotFoundException;

	// current month
	public List<StatementDTO> getCurrentMonthTransaction(Date transactionDate);

	// query param to date and from date
	public List<StatementDTO> getTransactionByDateRange(Date toDate, Date fromDate);

	// and last month, last 3 month
	public List<StatementDTO> getLastTransaction(Date transactionDate, int month);

}
