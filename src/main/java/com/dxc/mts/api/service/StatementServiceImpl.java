package com.dxc.mts.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.TransactionRepository;
import com.dxc.mts.api.dao.UserRepository;
import com.dxc.mts.api.dto.StatementDTO;
import com.dxc.mts.api.enums.BaseAppConstants;
import com.dxc.mts.api.exception.StatementNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.Transaction;
import com.dxc.mts.api.model.User;
import com.dxc.mts.api.util.Utility;

@Service
public class StatementServiceImpl implements StatementService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<StatementDTO> getTopTenTransaction(Long userId) throws NoSuchMessageException, StatementNotFoundException {
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
  public List<StatementDTO> getMonthTransaction(Date currentMonth, Long userId) throws NoSuchMessageException, StatementNotFoundException {
    int month = Utility.getMonthValue(currentMonth);
    List<Transaction> monthTransaction = transactionRepository.findMonthTransaction(month, userId);
    if (monthTransaction == null || monthTransaction.isEmpty()) {
      throw new StatementNotFoundException(messageSource.getMessage("mts.tx.not.found.message", null, null));
    }
    List<StatementDTO> listStatementDTOs = new LinkedList<>();
    for (Transaction transaction : monthTransaction) {
      listStatementDTOs.add(new StatementDTO(transaction));
    }
    return listStatementDTOs;
  }

  @Override
  public List<StatementDTO> getTransactionByDateRange(Date toDate, Date fromDate, Long userId)
      throws NoSuchMessageException, StatementNotFoundException, ParseException, UserNotFoundException {

    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new UserNotFoundException(messageSource.getMessage("mts.user.not.found.message", null, null));
    }
    toDate = new SimpleDateFormat(BaseAppConstants.YYYY_MM_DD.getValue()).parse(Utility.dateToStringIn_YYYY_MM_DD(toDate));
    fromDate = new SimpleDateFormat(BaseAppConstants.YYYY_MM_DD.getValue()).parse(Utility.dateToStringIn_YYYY_MM_DD(fromDate));

    List<Transaction> transactionList = transactionRepository.findByTransactionDateBetweenAndUser(toDate, fromDate, user.get());
    if (transactionList == null || transactionList.isEmpty()) {
      throw new StatementNotFoundException(messageSource.getMessage("mts.tx.not.found.message", null, null));
    }
    List<StatementDTO> listStatementDTOs = new LinkedList<>();
    for (Transaction transaction : transactionList) {
      listStatementDTOs.add(new StatementDTO(transaction));
    }
    return listStatementDTOs;
  }

  /**
   * Last 1 or 3 month Transactions
   * 
   * @throws UserNotFoundException
   *           when user not found
   * @throws ParseException
   *           when date format is not correct
   * @throws StatementNotFoundException
   *           when transaction not found
   * @throws NoSuchMessageException
   *           when message not found
   */
  @Override
  public List<StatementDTO> getLastMonthTransaction(Date currentDate, int lastMonth, Long userId)
      throws NoSuchMessageException, StatementNotFoundException, ParseException, UserNotFoundException {
    Date toDate = Utility.getLastMonthStartDate(lastMonth);
    Date fromDate = Utility.getLastMonthEndDate();
    return getTransactionByDateRange(toDate, fromDate, userId);
  }

}
