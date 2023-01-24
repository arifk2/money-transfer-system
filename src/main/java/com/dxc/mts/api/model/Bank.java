package com.dxc.mts.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author mkhan339
 *
 */
@Data
@NoArgsConstructor
@Entity(name = "BANK")
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BANK_ID")
	private Long bankId;

	@Column(name = "BANK_NAME")
	private String bankname;

	@Column(name = "BRANCH")
	private String branch;

	@Column(name = "IFSC_CODE")
	private String ifscCode;

	@OneToMany(mappedBy = "bank")
	private List<Account> accounts;

	@OneToOne(mappedBy = "bank")
	private Address address;

}
