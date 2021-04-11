package com.buchi.github.searcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommitRequest {
	
	private Integer pageSize;
	private Integer page;
	private String repo;
	private String from;
	private String to;
	private String author;
}
