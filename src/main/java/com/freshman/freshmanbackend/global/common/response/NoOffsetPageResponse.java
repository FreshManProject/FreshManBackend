package com.freshman.freshmanbackend.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collection;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoOffsetPageResponse extends SuccessResponse {
    private final Collection<?> list;
    private final int count;
    private final Boolean isEnd;
    private Long nextSeq;
    private Long nextPrice;
    private Integer nextOrderCount;

    public NoOffsetPageResponse(Collection<?> list, Boolean isEnd) {
        this.list = list;
        this.isEnd = isEnd;
        this.count = list == null ? 0 : list.size();
    }

    public NoOffsetPageResponse(Collection<?> list, Boolean isEnd, Long nextSeq, Long nextPrice,
                                Integer nextOrderCount) {
        this.list = list;
        this.isEnd = isEnd;
        this.count = list == null ? 0 : list.size();
        this.nextSeq = nextSeq;
        this.nextPrice = nextPrice;
        this.nextOrderCount = nextOrderCount;
    }

    public NoOffsetPageResponse(Collection<?> list, Boolean isEnd, Long nextSeq,
                                Integer nextOrderCount) {
        this.list = list;
        this.isEnd = isEnd;
        this.count = list == null ? 0 : list.size();
        this.nextSeq = nextSeq;
        this.nextOrderCount = nextOrderCount;
    }
}
