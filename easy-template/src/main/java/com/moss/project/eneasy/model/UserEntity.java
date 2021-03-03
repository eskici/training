package com.moss.project.eneasy.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "USER")
@Entity
@Setter
@Getter
@Where(clause = "recordStatus = 'A'")
public class UserEntity extends BaseModel {

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ROLE")
	private String role;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "AVATAR")
	private byte[] avatar;
	
	public UserEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public UserEntity(String username, String password,String role,String status,String email) {
		// TODO Auto-generated constructor stub
		this.username = username;
		this.password = password;
		this.role = role;
		this.status = status;
		this.email = email;
	}
}		  

