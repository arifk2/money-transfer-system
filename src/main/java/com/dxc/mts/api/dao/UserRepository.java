package com.dxc.mts.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxc.mts.api.model.User;

/**
 * 
 * @author mkhan339
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByEmailAddress(String emailAddress);
}
