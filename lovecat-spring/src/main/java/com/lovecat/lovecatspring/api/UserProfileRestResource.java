package com.lovecat.lovecatspring.api;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lovecat.lovecatspring.data.ProfileImage;
import com.lovecat.lovecatspring.data.ProfileImageRepository;
import com.lovecat.lovecatspring.data.UserProfile;
import com.lovecat.lovecatspring.data.UserProfileRepository;
import com.lovecat.lovecatspring.service.UserService;
import com.lovecat.lovecatspring.service.exception.UserExistsException;

@RestController
@RequestMapping("/users")
public class UserProfileRestResource {

	@Autowired
	UserService userService;
	
	@Autowired
	ProfileImageRepository imgRepo;

	@PostMapping
	public ResponseEntity<String> createUser(@RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("file") MultipartFile file) {
		System.out.println("Create user: " + username);
		try {
			@Valid
			UserProfilePrivateDto registerUser = new UserProfilePrivateDto();
			registerUser.setAdmin(false);
			registerUser.setUsername(username);
			registerUser.setPassword(password);
			userService.registerUser(registerUser);
			
			saveProfileImage(username, file);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (UserExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@GetMapping
	public Iterable<UserProfileDto> getAllUsers() {
		return userService.findAll();
	}


	
	@GetMapping("/{username}")
	public ResponseEntity<UserProfileDto> getUser(@PathVariable String username) {
		UserProfileDto user = userService.findByUsername(username);
		if (user != null) {
			return new ResponseEntity<UserProfileDto>( user, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserProfileDto>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{username}/image")
	public ResponseEntity<Resource> downloadFile(@PathVariable String username) {
		// Load file from database
		List<ProfileImage> dbFiles = imgRepo.findByUsername(username);
		
		ProfileImage first = dbFiles.get(0);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(first.getContentType()))
				// .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;
				// filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(first.getFile()));
	}
	
	@PostMapping("/{username}/image")
	public ResponseEntity<String> saveProfileImage(@PathVariable String username, 
			@RequestParam("file") MultipartFile file) {
		ProfileImage img = new ProfileImage();
		img.setUsername(username);
		img.setContentType(file.getContentType());
		try {
			img.setFile(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imgRepo.save(img);
		return ResponseEntity.ok().body("Ok!");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="/{username}/random", produces = {MediaType.APPLICATION_JSON_VALUE} )
	public HttpEntity<PagedResources<UserProfileDto>> randomUser(@PathVariable String username, Pageable pageable, PagedResourcesAssembler assembler) {
		Page<UserProfileDto> persons = userService.findNextOtherUser(username, pageable);
		Iterator<UserProfileDto> it = persons.iterator();
		
		while(it.hasNext()){
			UserProfileDto profile = it.next();
			profile.add(linkTo(methodOn(UserProfileRestResource.class).downloadFile(profile.getUsername())).withRel("img"));
		}
		
		
		return new ResponseEntity<>(assembler.toResource(persons), HttpStatus.OK);
	}

}
