package com.moss.project.eneasy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moss.project.eneasy.enums.EnumStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(schema = "RECOMMEND", name = "TOPIC")
@Entity
@Getter
@Setter
@Where(clause = "RECORD_STATUS = 'A'")
public class Topic  extends BaseEntity {

	@Column
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private EnumStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;

	@JsonIgnore
	@OneToMany(
			mappedBy = "topic",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private List<Entry> entries = new ArrayList<>();

}
