package com.dxc.mts.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dxc.mts.api.model.EventLog;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

	@Query(value = "SELECT * FROM event_log WHERE fk_user =:userId ORDER BY activity_timestamp DESC LIMIT 1 OFFSET 1", nativeQuery = true)
	public EventLog getLastLogin(@Param("userId") Long userId);
}
