package com.freshman.freshmanbackend.domain.question.repository;

import com.freshman.freshmanbackend.domain.question.domain.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 문의 리포지토리
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
  Page<Question> findByMember_MemberSeq(Long memberSeq, Pageable pageable);

  Page<Question> findByProduct_ProductSeq(Long productSeq, Pageable pageable);
}