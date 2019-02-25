package com.lovecat.lovecatspring.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.lovecat.lovecatspring.api.UserProfileDto;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long>{

	public Page<UserProfileDto> findByUsernameNot(String username, Pageable pageable, Class<UserProfileDto> class1);

	public UserProfile findByUsername(String username);

}
