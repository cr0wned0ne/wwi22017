package com.lovecat.lovecatspring.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProfileImageRepository extends CrudRepository<ProfileImage, Long> {

	List<ProfileImage> findByUsername(String username);

}
