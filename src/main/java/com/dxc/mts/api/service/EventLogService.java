package com.dxc.mts.api.service;

import com.dxc.mts.api.model.EventLog;

public interface EventLogService {

	public EventLog saveLoginEvent(String emailAddress);

	public EventLog lastLoginActivity(Long userId);

}
