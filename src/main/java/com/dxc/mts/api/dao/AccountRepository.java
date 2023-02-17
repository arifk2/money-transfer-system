package com.dxc.mts.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxc.mts.api.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Account findByAccountNumber(Long accountNumber);

}
