package com.lovecat.lovecatspring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lovecat.lovecatspring.api.UserProfileDto;
import com.lovecat.lovecatspring.api.UserProfilePrivateDto;
import com.lovecat.lovecatspring.data.UserProfile;
import com.lovecat.lovecatspring.data.UserProfileRepository;
import com.lovecat.lovecatspring.service.exception.UserExistsException;

@Service
public class UserService {
//implements UserDetailsService {

	@Autowired
	UserProfileRepository userRepo;

	@Transactional
	public UserProfile registerUser(@Valid UserProfilePrivateDto registerUser) throws UserExistsException {
		if (userExists(registerUser.getUsername())) {
			throw new UserExistsException();
		}

		UserProfile userProfile = new UserProfile();
		userProfile.setPassword(registerUser.getPassword());
		userProfile.setUsername(registerUser.getUsername());

		return userRepo.save(userProfile);

	}
	
	public Iterable<UserProfileDto> findAll() {
		List<UserProfileDto> userDtos = new ArrayList<UserProfileDto>();
		Iterable<UserProfile> users = userRepo.findAll();
		for (UserProfile user : users) {
			UserProfileDto dto = new UserProfileDto(user.getUsername());
			userDtos.add(dto);
		}
		
		return userDtos;
	}
	
	public Page<UserProfileDto> findNextOtherUser(String username, Pageable pageable) {
		return userRepo.findByUsernameNot(username, pageable, UserProfileDto.class);
	}

//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

//		UserProfile user = userRepo.findByUsername(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("No user found with username: " + username);
//		}
//		boolean enabled = true;
//		boolean accountNonExpired = true;
//		boolean credentialsNonExpired = true;
//		boolean accountNonLocked = true;
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword().toLowerCase(),
//				enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(user.getRoles()));
//	}

//	private static List<GrantedAuthority> getAuthorities(List<String> roles) {
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		for (String role : roles) {
//			authorities.add(new SimpleGrantedAuthority(role));
//		}
//		return authorities;
//	}

	private boolean userExists(String username) {
		if (userRepo.findByUsername(username) != null) {
			return true;
		} else {
			return false;
		}
	}

	public UserProfileDto findByUsername(String username) {
		UserProfile userEntitiy = userRepo.findByUsername(username);
		if (userEntitiy != null) {
			UserProfileDto userDto = new UserProfileDto(username);
			return userDto;
		} else  {
			return null;
		}
		
	}

}
