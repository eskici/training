package com.moss.project.eneasy.repository;

import java.util.List;

import com.moss.project.eneasy.enums.EnumStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.moss.project.eneasy.entity.User;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public class UserDAO extends HibernateDaoSupport {

	@SuppressWarnings("unchecked")
	public User findByName(String username){
		return (User) getHibernateTemplate().get(User.class, username);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> readLastUserEntitys(){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(User.class);
		criteria.setMaxResults(50);
		criteria.addOrder(Order.asc("lastChangeDate"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<User> readWaitingUserEntitys(){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(User.class);
		criteria.add(Expression.eq("status", EnumStatus.WAITING));
		criteria.addOrder(Order.asc("lastChangeDate"));
		return criteria.list();
	}
	
	public User readUserEntity(String objid){
		return (User) getHibernateTemplate().get(User.class, objid);
	}
	
	public void addNewUserEntity(User topic){
		try{
		getHibernateTemplate().persist(topic);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void updateUserEntity(User topic){
		getHibernateTemplate().update(topic);
	}	

}
