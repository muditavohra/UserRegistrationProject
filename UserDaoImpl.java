package com.example.synchrony.UserDao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.example.synchrony.userEntity.User;

@Repository
public abstract class UserDaoImpl implements UserDao{
	
	   @Autowired
	    private EntityManager em;

	    public User save(User user) {
	        Session session = em.unwrap(Session.class);
	        session.persist(user);
	        return user;
	    }

}
