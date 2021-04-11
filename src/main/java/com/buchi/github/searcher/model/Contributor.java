package com.buchi.github.searcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contributor {
	private String login;
	
	private Integer contributions;
	
	private String type;
	
	@JsonProperty("avatar_url")
	private String avartarUrl;
	
	@JsonProperty("site_admin")
	private Boolean siteAdmin;

}
