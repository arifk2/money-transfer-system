package com.dxc.mts.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dxc.mts.api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query(value = "select * from transactions where fk_user =:userId order by tx_timestamp desc limit 10", nativeQuery = true)
	List<Transaction> findtop10TxByTransactionDate(@Param("userId") Long userId);
}
