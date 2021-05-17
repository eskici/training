package com.moss.project.eneasy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Table(schema = "RECOMMEND", name = "USERS")
@Entity
@Setter
@Getter
@Builder
@Where(clause = "recordStatus = 'A'")
public class User extends BaseEntity {

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

	private String token;
	private boolean accountVerified;
	private int failedLoginAttempts;
	private boolean loginDisabled;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(schema = "RECOMMEND",
			name = "USER_AUTHORITY",
			joinColumns = { @JoinColumn(name = "USER_ID") },
			inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	private Set<Authority> authorities = new HashSet<>();

	public User() {
	}

	public void addUserAuthority(Authority group){
		authorities.add(group);
		group.getUsers().add(this);
	}

	public void removeUserGroups(Group group){
		authorities.remove(group);
		group.getUsers().remove(this);
	}
}

