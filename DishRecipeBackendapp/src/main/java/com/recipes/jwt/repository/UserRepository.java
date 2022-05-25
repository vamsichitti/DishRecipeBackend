package com.recipes.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipes.jwt.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUserName(String username);

}