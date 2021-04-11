package com.buchi.github.searcher.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Setter
@Getter
@Builder
public class PagedResponse<T> extends Response {

    private int pageNumber;
    private int pageSize;
    private long count;
    private List<T> data;

    public PagedResponse(List<T> data) {
        this.data = data;
        if (data != null && !data.isEmpty()) {
            this.count = data.size();
        }
    }

    public PagedResponse() {

    }

    public PagedResponse(int pageNumber, int pageSize, Integer count) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.count = count;
    }

    public PagedResponse(int pageNumber, int pageSize, long count, List<T> content) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.count = count;
        this.data = content;
    }
}
