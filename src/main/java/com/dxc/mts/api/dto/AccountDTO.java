package com.dxc.mts.api.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

	
	@NotNull(message = "${mts.account.no.missing}")
	private Long accountNumber;
	@NotNull(message = "${mts.account.type.missing}")
	private String accountType;
	private Double availableBalance;
	@NotNull(message = "${mts.user.id.missing}")
	private Long userId;
	@NotNull(message = "${mts.bank.id.missing}")
	private Long bankId;

}
