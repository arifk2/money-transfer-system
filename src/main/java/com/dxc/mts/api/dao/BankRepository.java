package com.dxc.mts.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxc.mts.api.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

	public Optional<Bank> findByIfscCode(String ifscCode);

}
