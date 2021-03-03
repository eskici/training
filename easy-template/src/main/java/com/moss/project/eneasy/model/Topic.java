package com.moss.project.eneasy.model;

import com.moss.project.eneasy.enums.TopicStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "TOPIC")
@Entity
@Getter
@Setter
@Where(clause = "RECORD_STATUS = 'A'")
public class Topic  extends BaseModel implements MafEntity<Long> {

	@Column
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private TopicStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity createdBy;

	@OneToMany(
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER
	)
	@JoinColumn(name = "TOPIC_ID")
	private List<Entry> entries = new ArrayList<>();

}
