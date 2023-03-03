package com.dxc.mts.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dxc.mts.api.model.Address;
import com.dxc.mts.api.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long>{
	
	public Optional<Bank> findBybankId(long addressId);
	
	
}
