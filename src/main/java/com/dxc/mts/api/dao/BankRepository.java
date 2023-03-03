package com.dxc.mts.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.mts.api.model.Bank;

/**
 * 
 * @author manasa
 *
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> 
	{

public Optional<Bank> findByBankname(String bankname);


}
