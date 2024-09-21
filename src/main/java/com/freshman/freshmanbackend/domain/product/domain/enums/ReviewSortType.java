package com.freshman.freshmanbackend.domain.product.domain.enums;

import lombok.Getter;

import java.util.EnumSet;

@Getter
public enum ReviewSortType {

    NEWEST("newest"),
    OLDEST("oldest");

    private final String code;

    ReviewSortType(String code) {
        this.code = code;
    }

    public static boolean containCode(String code) {
        return EnumSet.allOf(ReviewSortType.class).stream().anyMatch(e -> e.getCode().equals(code));
    }
}
