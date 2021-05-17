package com.moss.project.eneasy.service;

import com.moss.project.eneasy.entity.User;

public class BaseService implements IBaseService {


	public User getCurrentUser() {
/*
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (principal instanceof User && principal != null){
	    	User user  = (User)principal;
	    	return userDAO.readUserEntity(user.getUsername());
	    }
*/
	    return null;
	}

	
}
