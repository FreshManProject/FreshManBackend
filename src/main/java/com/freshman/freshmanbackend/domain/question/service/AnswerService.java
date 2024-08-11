package com.freshman.freshmanbackend.domain.question.service;

import com.freshman.freshmanbackend.domain.question.dao.AnswerDao;
import com.freshman.freshmanbackend.domain.question.domain.Answer;
import com.freshman.freshmanbackend.domain.question.domain.Question;
import com.freshman.freshmanbackend.domain.question.repository.AnswerRepository;
import com.freshman.freshmanbackend.domain.question.repository.QuestionRepository;
import com.freshman.freshmanbackend.domain.question.request.AnswerEntryRequest;
import com.freshman.freshmanbackend.domain.question.response.AnswerResponse;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 답변 서비스
 */
@Service
@RequiredArgsConstructor
public class AnswerService {
  private final AnswerRepository answerRepository;
  private final QuestionRepository questionRepository;
  private final AnswerDao answerDao;

  @Transactional(readOnly = true)
  public AnswerResponse getByQuestionSeq(Long questionId) {
    Answer answer = answerDao.getAnswerByQuestionSeq(questionId);
    if (answer == null) {
      throw new ValidationException("answer.not_found");
    }
    return AnswerResponse.of(answer);
  }

  @Transactional
  public void save(AnswerEntryRequest answerEntryRequest, Long questionId) {
    Answer answer = answerEntryRequest.toEntity();
    Question question =
        questionRepository.findById(questionId).orElseThrow(() -> new ValidationException("question.not_found"));
    question.addAnswer(answer);
    answerRepository.save(answer);
  }
}
