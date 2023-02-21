package com.dxc.mts.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxc.mts.api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
