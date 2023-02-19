package com.dxc.mts.api.dto;

import com.dxc.mts.api.model.Address;
import com.dxc.mts.api.model.Bank;
import com.dxc.mts.api.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {

	private Long addressId;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String country;
	private Long pinCode;
	private Long userId;
	private Long bankId;

	public AddressDTO(Address address, Bank bank, User user) {
		this.addressId = address.getAddressId();
		this.addressLine1 = address.getAddressLine1();
		this.addressLine2 = address.getAddressLine2();
		this.city = address.getCity();
		this.country = address.getCountry();
		this.pinCode = address.getPinCode();
		if (bank != null)
			this.bankId = bank.getBankId();
		if (user != null)
			this.userId = user.getUserId();
	}

}
