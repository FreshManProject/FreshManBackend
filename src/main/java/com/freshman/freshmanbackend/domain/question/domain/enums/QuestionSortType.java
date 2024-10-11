package com.freshman.freshmanbackend.domain.question.domain.enums;

import lombok.Getter;

@Getter
public enum QuestionSortType {
    NEWEST("newest"),
    OLDEST("oldest");

    private final String code;

    QuestionSortType(String code) {
        this.code = code;
    }
}
