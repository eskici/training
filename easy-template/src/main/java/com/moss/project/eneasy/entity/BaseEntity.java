package com.moss.project.eneasy.entity;

import java.util.Date;

import com.moss.project.eneasy.util.MyConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements MafEntity<Long>  {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
	@SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
	private Long id;

	@Column(name = "CREATION_DATE")
	@DateTimeFormat
	private Date creationDate;

	@Column(name = "LAST_CHANGE_DATE")
	@DateTimeFormat
	private Date lastChangeDate;

	@Column(name = "RECORD_STATUS")
	private String recordStatus;

	public BaseEntity() {
		this.setRecordStatus(MyConstants.RECORD_STATUS_ACTIVE);
		this.setCreationDate(new Date());
	}
}
