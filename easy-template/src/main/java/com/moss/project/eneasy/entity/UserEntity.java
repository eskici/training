package com.moss.project.eneasy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(schema = "RECOMMEND", name = "USERS")
@Entity
@Setter
@Getter
@Where(clause = "recordStatus = 'A'")
public class UserEntity extends BaseEntity {

	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "AVATAR")
	private byte[] avatar;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "RECOMMEND",
			name = "USER_AUTHORITY",
			joinColumns = { @JoinColumn(name = "USER_ID") },
			inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	private Set<Authority> authorities = new HashSet<>();

	public UserEntity() {
	}
}

