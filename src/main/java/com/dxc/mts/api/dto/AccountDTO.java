package com.dxc.mts.api.dto;

import javax.validation.constraints.NotNull;

import com.dxc.mts.api.model.Account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

	private Long accountId;
	@NotNull(message = "${mts.account.no.missing}")
	private Long accountNumber;
	@NotNull(message = "${mts.account.type.missing}")
	private String accountType;
	private Double availableBalance;
	@NotNull(message = "${mts.user.id.missing}")
	private Long userId;
	@NotNull(message = "${mts.bank.id.missing}")
	private Long bankId;

	public AccountDTO(Account account) {
		this.accountId = account.getAccountId();
		this.accountNumber = account.getAccountNumber();
		this.accountType = account.getAccountType();
		this.availableBalance = account.getAvailableBalance();
		this.userId = account.getUser().getUserId();
		this.bankId = account.getBank().getBankId();
	}
}
