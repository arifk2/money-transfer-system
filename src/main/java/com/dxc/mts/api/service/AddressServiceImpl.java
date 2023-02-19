package com.dxc.mts.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.AddressRepository;
import com.dxc.mts.api.dao.BankRepository;
import com.dxc.mts.api.dao.UserRepository;
import com.dxc.mts.api.dto.AccountDTO;
import com.dxc.mts.api.dto.AddressDTO;
import com.dxc.mts.api.exception.AddressNotFoundException;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.Account;
import com.dxc.mts.api.model.Address;
import com.dxc.mts.api.model.Bank;
import com.dxc.mts.api.model.User;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public AddressDTO saveOrUpdateAccount(AddressDTO addressDTO)
			throws NoSuchMessageException, UserNotFoundException, BankNotFoundException, AddressNotFoundException {
		Optional<User> userExist = null;
		Optional<Bank> bankExist = null;
		Address address = new Address();

		if (addressDTO.getAddressId() != null && addressDTO.getAddressId() != 0) {
			Optional<Address> addressExist = addressRepository.findByAddressId(addressDTO.getAddressId());
			if (addressExist.isEmpty()) {
				throw new AddressNotFoundException(
						messageSource.getMessage("mts.address.not.found.message", null, null));
			} else {
				address = addressExist.get();
			}
		}

		if (addressDTO != null && (addressDTO.getUserId() != null && addressDTO.getUserId() != 0)) {
			userExist = userRepository.findById(addressDTO.getUserId());
			if (userExist.isEmpty()) {
				throw new UserNotFoundException(messageSource.getMessage("mts.user.not.found.message", null, null));
			}
		} else if (addressDTO != null && (addressDTO.getBankId() != null || addressDTO.getBankId() != 0)) {
			bankExist = bankRepository.findById(addressDTO.getBankId());
			if (bankExist.isEmpty()) {
				throw new BankNotFoundException(messageSource.getMessage("mts.bank.not.found.message", null, null));
			}
		}

		address.setAddressLine1(addressDTO.getAddressLine1());
		address.setAddressLine2(addressDTO.getAddressLine2());
		address.setCity(addressDTO.getCity());
		address.setCountry(addressDTO.getCountry());
		address.setPinCode(addressDTO.getPinCode());
		if (userExist != null && !userExist.isEmpty()) {
			address.setUser(userExist.get());
		} else if (bankExist != null && !bankExist.isEmpty()) {
			address.setBank(bankExist.get());
		}
		Address saveAddress = addressRepository.save(address);
		if (saveAddress != null)
			return new AddressDTO(saveAddress, saveAddress.getBank(), saveAddress.getUser());
		return null;
	}

	@Override
	public AddressDTO getAddressId(long id) throws AddressNotFoundException {
		AddressDTO addressDTO = null;
		Optional<Address> address = addressRepository.findByAddressId(id);
		if (address.isEmpty()) {
			throw new AddressNotFoundException(messageSource.getMessage("mts.address.not.found.message", null, null));
		} else {
			if (address.get().getUser() != null) {
				addressDTO = new AddressDTO(address.get(), null, address.get().getUser());
			} else {
				addressDTO = new AddressDTO(address.get(), address.get().getBank(), null);
			}
		}
		return addressDTO;
	}

	@Override
	public List<AddressDTO> getAddress() {
		List<AddressDTO> addressDtoList = new ArrayList<>();
		List<Address> addresses = addressRepository.findAll();

		if (addresses != null && addresses.size() != 0) {
			for (Address address : addresses) {
				AddressDTO addressDTO = null;
				if (address.getUser() != null) {
					addressDTO = new AddressDTO(address, null, address.getUser());
				} else if (address.getBank() != null) {
					addressDTO = new AddressDTO(address, address.getBank(), null);
				}
				addressDtoList.add(addressDTO);
			}
		}
		return addressDtoList;
	}

}
