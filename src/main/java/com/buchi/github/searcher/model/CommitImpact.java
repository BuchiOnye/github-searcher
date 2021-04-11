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
public class CommitImpact {

	private String sha;
	@JsonProperty("stats")
	private CommitStat stat;
}
