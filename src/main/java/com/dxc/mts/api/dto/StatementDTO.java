package com.dxc.mts.api.dto;

import java.util.Date;

import com.dxc.mts.api.model.Transaction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatementDTO {

	private Long transactionId;
	private String transactionType;
	private String transferType;
	private Double transactionAmount;
	private Double openingBalance;
	private Double closingBalance;
	private Date transactionDate;
	private Date transactionTimestamp;
	private Long accountId;
	private Long userId;
	private String comments;
	private Long accountNumber;
	

	public StatementDTO(Transaction transaction) {
		this.transactionId = transaction.getTransactionId();
		this.transactionType = transaction.getTransactionType();
		this.transactionAmount = transaction.getTransactionAmount();
		this.openingBalance = transaction.getOpeningBalance();
		this.closingBalance = transaction.getClosingBalance();
		this.transactionDate = transaction.getTransactionDate();
		this.transactionTimestamp = transaction.getTransactionTimestamp();
		this.accountId = transaction.getAccount().getAccountId();
		this.userId = transaction.getUser().getUserId();
		this.comments = transaction.getComments();
		this.transferType = transaction.getTransferType();
	}
	
	public StatementDTO(Transaction transaction, AccountDTO accountDTO) {
		this.transactionId = transaction.getTransactionId();
		this.transactionType = transaction.getTransactionType();
		this.transactionAmount = transaction.getTransactionAmount();
		this.openingBalance = transaction.getOpeningBalance();
		this.closingBalance = transaction.getClosingBalance();
		this.transactionDate = transaction.getTransactionDate();
		this.transactionTimestamp = transaction.getTransactionTimestamp();
		this.accountId = transaction.getAccount().getAccountId();
		this.userId = transaction.getUser().getUserId();
		this.comments = transaction.getComments();
		this.transferType = transaction.getTransferType();
		
		this.accountNumber = accountDTO.getAccountNumber();
	}

}
