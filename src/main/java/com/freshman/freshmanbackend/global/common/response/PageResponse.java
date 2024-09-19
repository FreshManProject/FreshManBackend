package com.freshman.freshmanbackend.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Collection;

/**
 * 페이지 공통응답
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse extends SuccessResponse {
    private final Collection<?> list;
    private final int count;
    private final int totalCount;

    public PageResponse(Collection<?> list, int totalCount) {
        this.list = list;
        this.totalCount = totalCount;
        this.count = list == null ? 0 : list.size();
    }
}
