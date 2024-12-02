package com.green.greengramver2.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Paging {
    private final static int DEFAULT_PAGE_SIZE = 20;

    @Schema(example = "1", description = "Selected Page")
    private int page;
    @Schema(example = "20", description = "item count per page")
    private int size;
    @JsonIgnore
    private int startIdx;

    public Paging(Integer size, Integer page) {
        this.size = (size == null || size <= 0) ? DEFAULT_PAGE_SIZE : size;
        this.page = (page == null || page <= 0) ? 1 : page;
        this.startIdx = ( this.page - 1 ) * this.size;
    }
}