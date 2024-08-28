package com.example.game.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.game.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	public User findByUsername(String username);
}
