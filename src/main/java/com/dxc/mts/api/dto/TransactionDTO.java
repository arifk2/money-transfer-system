package com.dxc.mts.api.dto;

import java.util.Date;

import com.dxc.mts.api.model.Transaction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionDTO {

	private Long transactionID;
	private Long userId;
	private Long toAccountNumber;
	private Long fromAccountNumber;
	private Double transactionAmount;
	private String transactionType;
	private Date transactionDate;
	private Date transactionTimestamp;
	private String comments;
	private String status;

	public TransactionDTO(Transaction transaction, Long fromAccountNumber, String status) {
		this.transactionID = transaction.getTransactionId();
		this.userId = transaction.getUser().getUserId();
		this.toAccountNumber = transaction.getAccount().getAccountNumber();
		this.fromAccountNumber = fromAccountNumber;
		this.transactionAmount = transaction.getTransactionAmount();
		this.transactionType = transaction.getTransactionType();
		this.transactionDate = transaction.getTransactionDate();
		this.transactionTimestamp = transaction.getTransactionTimestamp();
		this.comments = transaction.getComments();
		this.status = status;
	}
}
