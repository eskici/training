package com.moss.project.eneasy.model;

import java.util.Set;

public class UserTransaction extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserEntity user;
	private Set<Entry> entries;
	private Set<Topic> topics;	

}
