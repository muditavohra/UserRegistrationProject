package com.example.synchrony.UserDao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.synchrony.userEntity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{
	
	User findByUsername(String username);
	
	User findByEmail(String email);

}
