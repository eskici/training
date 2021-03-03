package com.moss.project.eneasy.service;

import com.moss.project.eneasy.dao.UserDAO;
import com.moss.project.eneasy.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl implements IBaseService {


	public  UserEntity getCurrentUser() {
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
