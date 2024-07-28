package com.freshman.freshmanbackend.domain.question.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * 문의 목록 조회 Dao
 */
@Repository
@RequiredArgsConstructor
public class QuestionListDao {
  private final JPAQueryFactory queryFactory;
}
