package com.dxc.mts.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.AddressRequest;
import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.exception.AddressNotFoundException;
import com.dxc.mts.api.exception.ApplicationCustomException;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.model.Bank;
import com.dxc.mts.api.model.User;
import com.dxc.mts.api.service.AddressService;
import com.dxc.mts.api.util.JwtTokenUtil;

@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	private MessageSource source;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	/**
	 * This method is created to update user Address in the database.
	 * 
	 * @param address holds the address information for user
	 * @return response entity object
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 */
	@PutMapping("/updateUser")
	public ResponseEntity<?> updateUserAddress(@RequestHeader(name="Authorization") String token,@RequestBody AddressRequest address) throws NoSuchMessageException, ApplicationCustomException{
		String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
		final User userResponse=addressService.updateUserAddress(address, username);
		if (userResponse != null) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.CREATED.value(),
							source.getMessage("mts.user.updateaddress.success.message", null, null), userResponse),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * This method is created to update bank Address in the database.
	 * 
	 * @param  holds the address information for bank and bankId
	 * @return response entity object
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 */
	@PutMapping("/updateBank/{bankId}")
	public ResponseEntity<?> updateBankAddress(@RequestBody AddressRequest address,@PathVariable Long bankId) throws NoSuchMessageException, ApplicationCustomException{
		final Bank bankResponse=addressService.updateBankAddress(address, bankId);
		if (bankResponse != null) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.CREATED.value(),
							source.getMessage("mts.bank.updatebank.success.message", null, null), bankResponse),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAddressByUser")
	public ResponseEntity<?> getAddressByUserEmail(@RequestHeader(name="Authorization") String token)
			throws NoSuchMessageException, ApplicationCustomException, AddressNotFoundException, UserNotFoundException{
		String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
		final User userResponse=addressService.getAddressByEmailAddress(username);
		if (userResponse != null) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.CREATED.value(),
							source.getMessage("mts.user.address.found.message", null, null), userResponse),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAddressByBank/{bankId}")
	public ResponseEntity<?> getAddressByBankId(@PathVariable Long bankId) 
			throws NoSuchMessageException, ApplicationCustomException, AddressNotFoundException, BankNotFoundException{
		final Bank bankResponse=addressService.getAddressByBankId(bankId);
		if (bankResponse != null) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.CREATED.value(),
							source.getMessage("mts.bank.address.found.message", null, null), bankResponse),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
		}
	}
	

}
