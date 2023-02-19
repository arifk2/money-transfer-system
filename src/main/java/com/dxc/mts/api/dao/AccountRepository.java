package com.dxc.mts.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dxc.mts.api.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Account findByAccountNumber(Long accountNumber);

	public Optional<Account> findByAccountId(long id);

	@Query(value = "SELECT * FROM Account WHERE fk_user =:userId", nativeQuery = true)
	public List<Account> findByAccountUserId(@Param("userId") long userId);

	@Query(value = "SELECT * FROM Account WHERE fk_bank =:bankId", nativeQuery = true)
	public List<Account> findByAccountBankId(@Param("bankId") long bankId);
}
