package com.buchi.github.seacher.model;

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
	@JsonProperty("avatar_url")
	private String avartarUrl;
	
	private String login;
	
	private Integer contributions;
	
	private String type;
	
	@JsonProperty("site_admin")
	private Boolean siteAdmin;

}
