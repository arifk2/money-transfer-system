package com.dxc.mts.api.service;

import java.util.List;

import org.springframework.context.NoSuchMessageException;

import com.dxc.mts.api.dto.AddressDTO;
import com.dxc.mts.api.exception.AddressNotFoundException;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;

public interface AddressService {

	public AddressDTO saveOrUpdateAccount(AddressDTO addressDTO)
			throws UserNotFoundException, BankNotFoundException, NoSuchMessageException, AddressNotFoundException;

	public AddressDTO getAddressId(long id) throws AddressNotFoundException;

	public List<AddressDTO> getAddress();

}
