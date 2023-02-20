package com.dxc.mts.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.AddressDTO;
import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.exception.AddressNotFoundException;
import com.dxc.mts.api.exception.ApplicationCustomException;
import com.dxc.mts.api.exception.BankNotFoundException;
import com.dxc.mts.api.exception.UserNotFoundException;
import com.dxc.mts.api.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@Autowired
	private MessageSource source;

	/**
	 * This method is created to add/update address for user or bank
	 * 
	 * @param addressDTO holds the information of the accountDTO
	 * @return
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 */
	@PutMapping("/add-update")
	@Operation(summary = "Api to add/update address for user or bank")
	public ResponseEntity<?> saveAddress(@RequestBody AddressDTO addressDTO)
			throws NoSuchMessageException, ApplicationCustomException {
		AddressDTO accountDTOResponse = null;
		try {
			accountDTOResponse = addressService.saveOrUpdateAccount(addressDTO);
			if (accountDTOResponse != null) {
				return new ResponseEntity<Object>(
						new BaseResponse(HttpStatus.CREATED.value(),
								source.getMessage("mts.success.message", null, null), accountDTOResponse),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
						SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.user.not.found.message", null, null)),
					HttpStatus.NOT_FOUND);
		} catch (BankNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.bank.not.found.message", null, null)),
					HttpStatus.NOT_FOUND);
		} catch (AddressNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.address.not.found.message", null, null)),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method is created to get the address information based on the address id
	 * 
	 * @param id address id of the address
	 * @return response entity object
	 * @throws NoSuchMessageException     when no key is present
	 * @throws ApplicationCustomException application specific exception
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Api to get the address information based on the address id")
	public ResponseEntity<?> getAddress(@PathVariable long id)
			throws NoSuchMessageException, ApplicationCustomException {
		AddressDTO addressDTOResponse;
		try {
			addressDTOResponse = addressService.getAddressId(id);
			if (addressDTOResponse != null) {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(),
						source.getMessage("mts.success.message", null, null), addressDTOResponse), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(),
						SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.NOT_FOUND);
			}
		} catch (AddressNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.address.not.found.message", null, null)),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method is created to get list of addresses
	 * 
	 * @return response entity object
	 */
	@GetMapping
	@Operation(summary = "Api to get list of addresses")
	public ResponseEntity<?> getAddress() {
		final List<AddressDTO> addressDTOResponse = addressService.getAddress();
		if (addressDTOResponse != null) {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(),
					source.getMessage("mts.success.message", null, null), addressDTOResponse), HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.NOT_FOUND.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.NOT_FOUND);
		}
	}

}
