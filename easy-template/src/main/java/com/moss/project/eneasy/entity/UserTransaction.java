package com.moss.project.eneasy.entity;

import java.util.Set;

public class UserTransaction extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserEntity user;
	private Set<Entry> entries;
	private Set<Topic> topics;	

}
