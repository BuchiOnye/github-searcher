package com.buchi.github.seacher.constant;

public class AppConstants {
	// endpoints
	public static final String USER_REPOS = "https://api.github.com/search/repositories?q=";
	public static final String COMMITS = "https://api.github.com/repos/{username}/{repo}/commits?page={page}&per_page={per_page}&until={until}&since={since}";
	public static final String CONTRIBUTORS = "https://api.github.com/repos/{username}/{repo}/contributors?page={page}&per_page={per_page}";
	public static final String COMMIT_IMPACT = "https://api.github.com/repos/{username}/{repo}/commits/{sha}";

}
