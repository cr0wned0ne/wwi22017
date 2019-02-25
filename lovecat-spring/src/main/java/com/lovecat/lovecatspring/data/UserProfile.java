package com.lovecat.lovecatspring.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lovecat.lovecatspring.constants.LovecatConstants;

@Entity
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	
	private String username;
	
	private String password;
	
	private boolean admin;
	
	
	public UserProfile() {
		super();
	}


	public UserProfile(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public List<String> getRoles(){
		List<String> roles = new ArrayList<String>();
		
		if (isAdmin()) {
			roles.add(LovecatConstants.ROLE_ADMIN);
		} else {
			roles.add(LovecatConstants.ROLE_USER);
		}
		return roles;
	}
}
