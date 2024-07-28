package com.freshman.freshmanbackend.domain.question.repository;

import com.freshman.freshmanbackend.domain.question.domain.Question;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 문의 리포지토리
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
  
}
