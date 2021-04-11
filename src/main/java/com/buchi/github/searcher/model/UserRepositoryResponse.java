package com.buchi.github.searcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRepositoryResponse {
	
	private Long id;
	private String name;
    @JsonProperty("private")
	private Boolean pivateStatus;
	private String description;
	private String url;
	private Integer size;
    @JsonProperty("owner")
	private Owner owner;
    @JsonProperty("created_at")
    private String createdAt;

}
