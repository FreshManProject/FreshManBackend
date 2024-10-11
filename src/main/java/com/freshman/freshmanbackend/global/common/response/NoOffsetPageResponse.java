package com.freshman.freshmanbackend.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Collection;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoOffsetPageResponse extends SuccessResponse{
    private final Collection<?> list;
    private final int count;
    private final Boolean isEnd;

    public NoOffsetPageResponse(Collection<?> list, Boolean isEnd) {
        this.list = list;
        this.isEnd = isEnd;
        this.count = list == null ? 0 : list.size();
    }
}
