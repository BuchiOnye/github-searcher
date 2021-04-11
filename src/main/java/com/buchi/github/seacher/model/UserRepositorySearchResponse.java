package com.buchi.github.seacher.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRepositorySearchResponse {
	@JsonProperty("total_count")
	private Integer count;
	
	@JsonProperty("items")
	private List<UserRepositoryResponse> response;

}
