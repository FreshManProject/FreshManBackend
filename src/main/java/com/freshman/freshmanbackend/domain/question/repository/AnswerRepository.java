package com.freshman.freshmanbackend.domain.question.repository;

import com.freshman.freshmanbackend.domain.question.domain.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 답변 리포지토리
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
