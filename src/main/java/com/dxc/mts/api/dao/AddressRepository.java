package com.dxc.mts.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dxc.mts.api.model.Address;
//@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{
//	public Optional<Address> findByAddressId(long addressId);
	
	@Query("SELECT a FROM ADDRESS a WHERE a.user.userId=:user_id")
	public Optional<Address> findAddressByUsersId(@Param("user_id") Long userId);	
	
	@Query("SELECT a FROM ADDRESS a WHERE a.bank.bankId=:bank_id")
	public Optional<Address> findAddressByBanksId(@Param("bank_id") Long bankId);
}
