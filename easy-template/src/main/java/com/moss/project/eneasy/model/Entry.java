package com.moss.project.eneasy.model;

import com.moss.project.eneasy.enums.TopicStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Table(name = "ENTRY")
@Entity
@Setter
@Getter
@Where(clause = "RECORD_STATUS = 'A'")
public class Entry extends BaseModel implements MafEntity<Long> {

	@Column(name = "CONTENT")
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private TopicStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity createdBy;

	@ManyToOne(fetch = FetchType.EAGER)
	private Topic topic;
}
