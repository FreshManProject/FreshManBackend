package com.freshman.freshmanbackend.domain.question.dao;

import com.freshman.freshmanbackend.domain.question.domain.Answer;
import com.freshman.freshmanbackend.domain.question.domain.QAnswer;
import com.freshman.freshmanbackend.domain.question.domain.QQuestion;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * 답변 Dao
 */
@RequiredArgsConstructor
@Repository
public class AnswerDao {
  private final JPAQueryFactory jpaQueryFactory;

  public Answer getAnswerByQuestionSeq(Long questionSeq) {
    QAnswer qAnswer = QAnswer.answer;
    QQuestion qQuestion = QQuestion.question;
    return jpaQueryFactory.select(qAnswer)
                          .from(qQuestion)
                          .join(qQuestion.answer, qAnswer)
                          .where(qQuestion.questionSeq.eq(questionSeq))
                          .fetchOne();
  }
}
