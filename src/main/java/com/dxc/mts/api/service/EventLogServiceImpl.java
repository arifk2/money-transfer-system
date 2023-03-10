package com.dxc.mts.api.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.mts.api.dao.EventLogRepository;
import com.dxc.mts.api.dao.UserRepository;
import com.dxc.mts.api.enums.BaseAppConstants;
import com.dxc.mts.api.model.EventLog;
import com.dxc.mts.api.model.User;

@Service
public class EventLogServiceImpl implements EventLogService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventLogRepository eventLogRepository;

	@Override
	public EventLog saveLoginEvent(String emailAddress) {
		EventLog eventLog = new EventLog();
		eventLog.setActivityTimeStamp(new Date());
		eventLog.setCurrentTimestamp(new Date());
		eventLog.setActivity(BaseAppConstants.LOGIN.getValue());

		Optional<User> user = userRepository.findByEmailAddress(emailAddress);
		if (!user.isEmpty()) {
			eventLog.setUser(user.get());
			return eventLogRepository.save(eventLog);
		}
		return null;
	}
	
	@Override
	public EventLog lastLoginActivity(Long userId) {
		return eventLogRepository.getLastLogin(userId);
	}
	

}
