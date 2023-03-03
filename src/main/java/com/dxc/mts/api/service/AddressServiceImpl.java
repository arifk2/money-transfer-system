package com.dxc.mts.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.AddressRepository;
import com.dxc.mts.api.dao.BankRepository;
import com.dxc.mts.api.dao.UserRepository;
import com.dxc.mts.api.dto.AddressRequest;
import com.dxc.mts.api.exception.AddressNotFoundException;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.Address;
import com.dxc.mts.api.model.Bank;
import com.dxc.mts.api.model.User;

@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AddressRepository addressRepository;	
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private UserRepository userRepository;

	/**
	 * updating user address
	 * */
	@Override
	public User updateUserAddress(AddressRequest address, String username) {
		Optional<User> userExist = userRepository.findByEmailAddress(username);
		User user=null;
		if(userExist.isPresent()) {
			user=userExist.get();
			long userId=user.getUserId();
			Optional<Address> userAddressExist = addressRepository.findAddressByUsersId(userId);
			if(userAddressExist.isPresent()) {
				Address userAddress=userAddressExist.get();				
				userAddress.setAddressId(userAddress.getAddressId());
				userAddress.setAddressLine1(address.getAddressLine1()!=null?address.getAddressLine1():userAddress.getAddressLine1());
				userAddress.setAddressLine2(address.getAddressLine2()!=null?address.getAddressLine2():userAddress.getAddressLine2());
				userAddress.setCity(address.getCity()!=null?address.getCity():userAddress.getCity());
				userAddress.setCountry(address.getCountry()!=null?address.getCountry():userAddress.getCountry());
				userAddress.setPinCode(address.getPinCode()!=null?address.getPinCode():userAddress.getPinCode());
				user.setAddress(userAddress);				
			}else {
				Address newUserAddress=new Address();
				newUserAddress.setAddressLine1(address.getAddressLine1());
				newUserAddress.setAddressLine2(address.getAddressLine2());
				newUserAddress.setCity(address.getCity());
				newUserAddress.setCountry(address.getCountry());
				newUserAddress.setPinCode(address.getPinCode());
				newUserAddress.setUser(user);
				newUserAddress=addressRepository.save(newUserAddress);
				user.setAddress(newUserAddress);
			}
		}
		return userRepository.save(user);
	}
	
	/**
	 * updating bank address
	 * */
	@Override
	public Bank updateBankAddress(AddressRequest address, Long bankId) {
		Optional<Bank> bankExist = bankRepository.findById(bankId);
		Bank bank=null;
		if(bankExist.isPresent()) {
			bank=bankExist.get();
			Optional<Address> bankAddressExist = addressRepository.findAddressByBanksId(bankId);
			if(bankAddressExist.isPresent()) {
				Address bankAddress=bankAddressExist.get();				
				bankAddress.setAddressId(bankAddress.getAddressId());
				bankAddress.setAddressLine1(address.getAddressLine1()!=null?address.getAddressLine1():bankAddress.getAddressLine1());
				bankAddress.setAddressLine2(address.getAddressLine2()!=null?address.getAddressLine2():bankAddress.getAddressLine2());
				bankAddress.setCity(address.getCity()!=null?address.getCity():bankAddress.getCity());
				bankAddress.setCountry(address.getCountry()!=null?address.getCountry():bankAddress.getCountry());
				bankAddress.setPinCode(address.getPinCode()!=null?address.getPinCode():bankAddress.getPinCode());
				bank.setAddress(bankAddress);				
			}else {
				Address newBankAddress=new Address();
				newBankAddress.setAddressLine1(address.getAddressLine1());
				newBankAddress.setAddressLine2(address.getAddressLine2());
				newBankAddress.setCity(address.getCity());
				newBankAddress.setCountry(address.getCountry());
				newBankAddress.setPinCode(address.getPinCode());
				newBankAddress.setBank(bank);
				newBankAddress=addressRepository.save(newBankAddress);
				bank.setAddress(newBankAddress);
				addressRepository.save(newBankAddress);
				bank.setAddress(newBankAddress);
			}
		}
		return bankRepository.save(bank);
	}
	
	@Override
	public User getAddressByEmailAddress(String email)
			throws NoSuchMessageException, UserNotFoundException, AddressNotFoundException {
		Optional<User> userExist = userRepository.findByEmailAddress(email);
		if(userExist.isEmpty())
			throw new UserNotFoundException(messageSource.getMessage("mts.user.not.found.message", null, null));
		else {
			User user= userExist.get();
			if(user.getAddress()!=null)
				return user;
		}
		throw new AddressNotFoundException(messageSource.getMessage("mts.user.address.not.found.message",null, null));
	}

	@Override
	public Bank getAddressByBankId(Long bankId)
			throws NoSuchMessageException, AddressNotFoundException, BankNotFoundException, AddressNotFoundException {
		Optional<Bank> bankExist = bankRepository.findBybankId(bankId);
		if(bankExist.isEmpty())
			throw new BankNotFoundException(messageSource.getMessage("mts.bank.not.found.message", null, null));
		else {
			Bank bank= bankExist.get();
			if(bank.getAddress()!=null)
				return bank;
		}
		throw new AddressNotFoundException(messageSource.getMessage("mts.bank.address.not.found.message",null, null));
	}

}
