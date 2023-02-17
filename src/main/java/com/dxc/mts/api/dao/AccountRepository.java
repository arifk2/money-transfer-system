package com.dxc.mts.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxc.mts.api.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Account findByAccountNumber(Long accountNumber);

	public Optional<Account> findByAccountId(long id);

}
