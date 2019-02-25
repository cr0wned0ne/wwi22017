package com.lovecat.lovecatspring.api;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileDto extends ResourceSupport{
	
	@NotNull
	@NotEmpty
	private String username;
	
	@JsonCreator
	public UserProfileDto(@JsonProperty("username") String username) {
		super();
		this.username = username;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
