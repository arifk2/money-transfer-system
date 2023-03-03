package com.dxc.mts.api.service;

import org.springframework.context.NoSuchMessageException;

import com.dxc.mts.api.dto.AddressRequest;
import com.dxc.mts.api.exception.AddressNotFoundException;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.Bank;
import com.dxc.mts.api.model.User;

public interface AddressService {
	public User updateUserAddress(AddressRequest address,String username);
	public Bank updateBankAddress(AddressRequest address,Long bankId);
	public User getAddressByEmailAddress(String email) throws NoSuchMessageException, AddressNotFoundException ,UserNotFoundException,AddressNotFoundException;
	public Bank getAddressByBankId(Long bankId)  throws NoSuchMessageException, AddressNotFoundException,BankNotFoundException,AddressNotFoundException ;

}
