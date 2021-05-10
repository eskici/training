package com.moss.project.eneasy.dao;

import java.util.List;

import com.moss.project.eneasy.enums.EnumStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.moss.project.eneasy.entity.UserEntity;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public class UserDAO extends HibernateDaoSupport {

	@SuppressWarnings("unchecked")
	public UserEntity findByName(String username){
		return (UserEntity) getHibernateTemplate().get(UserEntity.class, username);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserEntity> readLastUserEntitys(){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(UserEntity.class);
		criteria.setMaxResults(50);
		criteria.addOrder(Order.asc("lastChangeDate"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> readWaitingUserEntitys(){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Expression.eq("status", EnumStatus.WAITING));
		criteria.addOrder(Order.asc("lastChangeDate"));
		return criteria.list();
	}
	
	public UserEntity readUserEntity(String objid){
		return (UserEntity) getHibernateTemplate().get(UserEntity.class, objid);
	}
	
	public void addNewUserEntity(UserEntity topic){
		try{
		getHibernateTemplate().persist(topic);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void updateUserEntity(UserEntity topic){
		getHibernateTemplate().update(topic);
	}	

}
