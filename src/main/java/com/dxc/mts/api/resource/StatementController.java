package com.dxc.mts.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.dto.StatementDTO;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.exception.StatementNotFoundException;
import com.dxc.mts.api.service.StatementService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/statement")
public class StatementController {

	@Autowired
	private StatementService statementService;

	@Autowired
	private MessageSource source;

	@GetMapping("/top10/{userId}")
	@Operation(summary = "Api to get Top ten Transaction")
	public ResponseEntity<?> getToTenTransaction(@PathVariable Long userId) {
		List<StatementDTO> statementResponse = null;
		try {
			statementResponse = statementService.getTopTenTransaction(userId);
			if (statementResponse != null) {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(),
						source.getMessage("mts.success.message", null, null), statementResponse), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
						SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
			}
		} catch (StatementNotFoundException e) {
			return new ResponseEntity<Object>(
					new BaseResponse(HttpStatus.NOT_FOUND.value(), SecurityError.OPERATION_FAILED.getDescription(),
							source.getMessage("mts.to.from.account.not.found.message", null, null)),
					HttpStatus.NOT_FOUND);
		}
	}
}
