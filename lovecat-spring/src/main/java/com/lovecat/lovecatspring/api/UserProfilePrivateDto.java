package com.lovecat.lovecatspring.api;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.lovecat.lovecatspring.data.UserProfile;

public class UserProfilePrivateDto extends UserProfile{
	
	@NotNull
	@NotEmpty
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
