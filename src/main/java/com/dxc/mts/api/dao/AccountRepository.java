package com.dxc.mts.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.mts.api.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	 public Optional<Account> findByAccountId(Long accountId);
}
