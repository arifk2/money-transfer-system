package com.dxc.mts.api.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.context.NoSuchMessageException;

import com.dxc.mts.api.dto.StatementDTO;
import com.dxc.mts.api.exception.StatementNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;

/*
 * 
 * Last 1 or 3 month Transactions
Between date range
 * 
 * */
public interface StatementService {

  public List<StatementDTO> getTopTenTransaction(Long userId) throws StatementNotFoundException;

  // current month or any month transaction
  public List<StatementDTO> getMonthTransaction(Date currentMonth, Long userId) throws NoSuchMessageException, StatementNotFoundException;

  // query param to date and from date
  public List<StatementDTO> getTransactionByDateRange(Date toDate, Date fromDate, Long userId)
      throws NoSuchMessageException, StatementNotFoundException, ParseException, UserNotFoundException;

  // and last month, last 3 month
  public List<StatementDTO> getLastMonthTransaction(Date currentDate, int lastMonth, Long userId)
      throws NoSuchMessageException, StatementNotFoundException, ParseException, UserNotFoundException;

}
