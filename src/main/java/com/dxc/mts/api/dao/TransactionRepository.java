package com.dxc.mts.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dxc.mts.api.model.Transaction;
import com.dxc.mts.api.model.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query(value = "select * from transactions where fk_user =:userId order by tx_timestamp desc limit 10", nativeQuery = true)
	List<Transaction> findtop10TxByTransactionDate(@Param("userId") Long userId);

	@Query(value = "select * from transactions where MONTH(tx_date) =:monthValue and fk_user =:userId", nativeQuery = true)
	List<Transaction> findMonthTransaction(@Param("monthValue") int month, @Param("userId") Long userId);

	// @Query(value = "select * from transactions where tx_date BETWEEN
	// CAST(=:toDateS AS DATE) AND CAST(=:fromDateS AS DATE)", nativeQuery = true)
	List<Transaction> findByTransactionDateBetweenAndUser(Date toDateS, Date fromDateS, User user);
}
