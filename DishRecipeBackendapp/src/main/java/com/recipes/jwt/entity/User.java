package com.recipes.jwt.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="USER_TBL")
public class User {
	
	@Id
	private int id;
	@Column
	private String userName;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String role;

	public User(int id, String userName, String password, String email,String role) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role=role;
	}
	

	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public User() {
		
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
