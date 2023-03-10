package com.dxc.mts.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.mts.api.dto.BaseResponse;
import com.dxc.mts.api.enums.SecurityError;
import com.dxc.mts.api.model.EventLog;
import com.dxc.mts.api.service.EventLogService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/event")
public class EventLogController {

	@Autowired
	private EventLogService eventLogService;

	@Autowired
	private MessageSource source;

	/**
	 * This api is created to get last login activity
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/{userId}")
	@Operation(summary = "Api to get Last Login Activity")
	public ResponseEntity<?> getLastLoginActivity(@PathVariable Long userId) {
		EventLog lastEventLog = eventLogService.lastLoginActivity(userId);
		if (lastEventLog != null) {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.OK.value(),
					source.getMessage("mts.success.message", null, null), lastEventLog), HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new BaseResponse(HttpStatus.BAD_REQUEST.value(),
					SecurityError.OPERATION_FAILED.getDescription(), null), HttpStatus.BAD_REQUEST);
		}
	}

}
