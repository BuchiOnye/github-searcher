package com.buchi.github.seacher.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommitStat implements Serializable {
	
	private Integer total;
	private Integer additions;
	private Integer deletions;

}
