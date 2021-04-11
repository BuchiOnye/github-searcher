package com.buchi.github.seacher.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buchi.github.seacher.model.CommitRequest;
import com.buchi.github.seacher.model.CommitResponse;
import com.buchi.github.seacher.model.PagedResponse;
import com.buchi.github.seacher.service.UserService;

import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value = {"/repository","/repository/{user_name}"})
	public ResponseEntity<?> getUserRepositories(@ApiIgnore Principal principal, @PathVariable(name = "user_name", required = false) String userName,
			@RequestParam(name = "search", defaultValue = "") String searchParam ){
		return userService.getUserRepositoriesBySearchParam(principal, userName, searchParam);
	}
	
	@GetMapping(value = {"/commit","/commit/{user_name}"})
	public PagedResponse<CommitResponse> getCommits(@ApiIgnore Principal principal, @PathVariable(name = "user_name", required = false) String userName,
			@RequestParam(name = "page_size", defaultValue = "100") Integer pageSize,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "repo",required = true) String repo,
			@RequestParam(name = "since", defaultValue = "1997-01-11T00:30:37Z") String from,
			@RequestParam(name = "until", defaultValue = "") String to,
			@RequestParam(name = "author", defaultValue = "") String author){

		CommitRequest request =  CommitRequest.builder().from(from)
				.page(page).pageSize(pageSize).repo(repo)
				.to(to).author(author).build();
		
		return userService.getCommitsByUser(principal, userName, request);
	}
	
	
	@GetMapping(value = {"/contributor","/contributor/{user_name}"})
	public ResponseEntity<?> getContributors(@ApiIgnore Principal principal, @PathVariable(name = "user_name", required = false) String userName,
			@RequestParam(name = "page_size", defaultValue = "100") Integer pageSize,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "repo",required = true) String repo){

		CommitRequest request =  CommitRequest.builder()
				.page(page).pageSize(pageSize).repo(repo)
				.build();
		
		return userService.getRepositoryContributors(principal, userName, request);
	}


}
