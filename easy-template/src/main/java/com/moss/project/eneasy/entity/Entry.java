package com.moss.project.eneasy.entity;

import com.moss.project.eneasy.enums.EnumStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Table(schema = "RECOMMEND", name = "ENTRY")
@Entity
@Setter
@Getter
@Where(clause = "RECORD_STATUS = 'A'")
public class Entry extends BaseEntity {

	@Column(name = "CONTENT", length = 5000)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private EnumStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;

	@ManyToOne(fetch = FetchType.EAGER)
	private Topic topic;
}
