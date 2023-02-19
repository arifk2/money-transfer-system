package com.dxc.mts.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxc.mts.api.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findByAddressId(long id);

}
