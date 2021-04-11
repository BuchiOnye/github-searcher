package com.buchi.github.seacher.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commit implements Serializable {
	private Author author;
	private Author committer;
	private String url;
	private String message;
	@JsonProperty("comment_count")
	private Integer commentCount;
	private CommitStat commitStatus;
}
