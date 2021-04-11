package com.buchi.github.seacher;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.buchi.github.seacher.ConsoleApplication;
import com.buchi.github.seacher.model.CommitRequest;
import com.buchi.github.seacher.model.CommitResponse;
import com.buchi.github.seacher.model.PagedResponse;
import com.buchi.github.seacher.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConsoleApplication.class)
class ConsoleApplicationTests {

	@Autowired
	UserService userService;

	CommitRequest request;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void repositorySearchTest() {

		ResponseEntity<?> response = userService.getUserRepositoriesBySearchParam(null, "BuchiOnye", "react-app");
		assertDoesNotThrow(() -> response);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void getCommitsByUserTest() {
		request = CommitRequest.builder().author("sfxookpaleke")
				.from("1997-01-18T06:30:37Z").page(1).pageSize(10).repo("profile-image-store").to("").build();
				
		PagedResponse<CommitResponse> response = userService.getCommitsByUser(null, "BuchiOnye", request);
		assertDoesNotThrow(() -> response);

		Assert.assertEquals("200", response.getCode());

	}
	
	@Test
	public void getRepositoryContibutorsTest() {
		request = CommitRequest.builder().page(1).pageSize(10).repo("react-app").build();
		ResponseEntity<?> response = userService.getRepositoryContributors(null, "BuchiOnye", request);
		assertDoesNotThrow(() -> response);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}

}
