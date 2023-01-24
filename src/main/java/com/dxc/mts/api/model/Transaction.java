package com.dxc.mts.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author mkhan339
 *
 */
@Data
@NoArgsConstructor
@Entity(name = "TRANSACTIONS")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TX_ID")
	private Long transactionId;

	@Column(name = "TX_TYPE")
	private String transactionType;

	@Column(name = "TX_AMOUNT")
	private Double transactionAmount;

	@Column(name = "OPENING_BALANCE")
	private Double openingBalance;

	@Column(name = "CLOSING_BALANCE")
	private Double closingBalance;

	@Column(name = "TX_DATE")
	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	@Column(name = "TX_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionTimestamp;

	@ManyToOne
	@JoinColumn(name = "FK_ACCOUNT")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "FK_USER")
	private User user;

}
