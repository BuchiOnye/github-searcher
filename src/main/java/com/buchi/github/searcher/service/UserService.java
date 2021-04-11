package com.buchi.github.searcher.service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.buchi.github.searcher.constant.AppConstants;
import com.buchi.github.searcher.exception.BadRequestException;
import com.buchi.github.searcher.exception.NotFoundException;
import com.buchi.github.searcher.model.CommitImpact;
import com.buchi.github.searcher.model.CommitRequest;
import com.buchi.github.searcher.model.CommitResponse;
import com.buchi.github.searcher.model.Contributor;
import com.buchi.github.searcher.model.PagedResponse;
import com.buchi.github.searcher.model.UserRepositorySearchResponse;
import com.buchi.github.searcher.utils.GithubPrincipalExtractor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	RestTemplate restTemplate;

	public ResponseEntity<UserRepositorySearchResponse> getUserRepositoriesBySearchParam(Principal principal, String username, String searchParam) {
		String accountName= retrieveUserLogin(principal, username);

		HttpEntity<UserRepositorySearchResponse> response;
		try {
			response = restTemplate.exchange(AppConstants.USER_REPOS.concat(searchParam).concat(" in:name user:").concat(accountName), HttpMethod.GET, null,  UserRepositorySearchResponse.class);
		} catch (RestClientException exception) {
			throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "No result found!");
		}

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}

	@Cacheable(value = "commit_response", key = "{#username,#request.repo}")
	public PagedResponse<CommitResponse> getCommitsByUser(Principal principal, String username, CommitRequest request) {
		String accountName = retrieveUserLogin(principal, username);

		StringBuilder url = new StringBuilder();
		url.append(AppConstants.COMMITS.replace("{username}", accountName)
				.replace("{repo}", request.getRepo())
				.replace("{page}", request.getPage().toString())
				.replace("{per_page}", request.getPageSize().toString())
				.replace("{until}", request.getTo())
				.replace("{since}", request.getFrom()));
		if(StringUtils.isNotBlank(request.getAuthor())) {
			url.append("&author="+request.getAuthor());
		}

		HttpEntity<CommitResponse[]> response;
		try {
			response = restTemplate.exchange(url.toString(), HttpMethod.GET, null,  CommitResponse[].class);
		} catch (RestClientException exception) {
			log.error("Could not retrieve details ", exception);
			throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "No result found!");
		}

		List<CommitResponse> commitResponse = Arrays.asList(response.getBody());
		if(!commitResponse.isEmpty()) {
			commitResponse = getCommitStatus(request.getRepo(), accountName, commitResponse);
		}

		return new PagedResponse<CommitResponse>(request.getPage(), request.getPageSize(), commitResponse.size(),  commitResponse);
	}

	private List<CommitResponse> getCommitStatus(String repo, String accountName, List<CommitResponse> response) {
		String commitUrl = AppConstants.COMMIT_IMPACT.replace("{username}", accountName)
				.replace("{repo}", repo);
		HttpEntity<CommitImpact> statResponse;

		for(CommitResponse commitResponse : response) {
			commitUrl = commitUrl.replace("{sha}", commitResponse.getSha());
			try {
				statResponse = restTemplate.exchange(commitUrl, HttpMethod.GET, null,  CommitImpact.class);
				commitResponse.getCommit().setCommitStatus(statResponse.getBody().getStat());
			} catch (RestClientException exception) {
				log.error("Could not retrieve details ", exception);
				throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "No result found!");
			}
		}

		return response;
	}

	public ResponseEntity<List<Contributor>> getRepositoryContributors(Principal principal, String username, CommitRequest request) {
		String accountName = retrieveUserLogin(principal, username);

		StringBuilder url = new StringBuilder();
		url.append(AppConstants.CONTRIBUTORS.replace("{username}", accountName)
				.replace("{repo}", request.getRepo()).replace("{page}", request.getPage().toString())
				.replace("{per_page}", request.getPageSize().toString()));

		HttpEntity<Contributor[]> response;
		try {
			response = restTemplate.exchange(url.toString(), HttpMethod.GET, null,  Contributor[].class);
		} catch (RestClientException exception) {
			log.error("Could not retrieve contributors for this repository ", exception);
			throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "No result found!");
		}

		return new ResponseEntity<>(Arrays.asList(response.getBody()) , HttpStatus.OK);
	}

	private String retrieveUserLogin(Principal principal, String username) {
		String accountName = StringUtils.isNotBlank(username) ?  username : GithubPrincipalExtractor.retrieveUserLoginDetails(principal);
		if(StringUtils.isBlank(accountName)) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),  "Could not retrieve account name!");
		}

		return accountName;
	}

}
