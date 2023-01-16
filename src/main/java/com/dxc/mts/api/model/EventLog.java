package com.dxc.mts.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author mkhan339
 *
 */
@Data
@NoArgsConstructor
@Entity(name = "EVENT_LOG")
public class EventLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EVENT_ID")
	private Long eventId;

	@Column(name = "CURRENT_TIMESTAMPS")
	@Temporal(TemporalType.TIMESTAMP)
	private Date currentTimestamp;

	@Column(name = "ACTIVITY_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date activityTimeStamp;

	@Column(name = "ACTIVITY")
	private String activity;

	@ManyToOne
	@JoinColumn(name = "FK_USER")
	private User user;

}
