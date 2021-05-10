package com.moss.project.eneasy.service;

import java.util.List;

import lombok.AllArgsConstructor;

import com.moss.project.eneasy.dao.UserDAO;
import com.moss.project.eneasy.entity.UserEntity;

@AllArgsConstructor
public class UserService implements IUserService {

	private UserDAO userDAO;
	
	public List<UserEntity> readLastUserEntitys() {
		return userDAO.readLastUserEntitys();
	}

	public void approveUserEntity(String objid) {
		UserEntity topic = userDAO.readUserEntity(objid);
		topic.setStatus("ON");
		userDAO.updateUserEntity(topic);		
	}

	public void cancelUserEntity(String objid) {
		UserEntity topic = userDAO.readUserEntity(objid);
		topic.setStatus("IP");
		userDAO.updateUserEntity(topic);
	}

	public void addNewUserEntity(UserEntity user) {
		userDAO.addNewUserEntity(user);
	}

	public UserEntity readUserEntity(String objid) {
		return userDAO.readUserEntity(objid);
	}

	public List<UserEntity> readWaitingUserEntitys() {
		return userDAO.readWaitingUserEntitys();
	}

	public List<UserEntity> searchUserEntity(String title) {
		return null;
	}
}
