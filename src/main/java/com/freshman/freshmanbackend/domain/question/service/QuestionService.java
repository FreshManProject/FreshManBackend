package com.freshman.freshmanbackend.domain.question.service;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;
import com.freshman.freshmanbackend.domain.question.domain.Question;
import com.freshman.freshmanbackend.domain.question.repository.QuestionRepository;
import com.freshman.freshmanbackend.domain.question.request.QuestionEntryRequest;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.common.domain.enums.Valid;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * 질문 서비스
 */
@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionRepository questionRepository;
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;

  /**
   * 문의 삭제
   *
   * @param questionSeq
   */
  public void delete(Long questionSeq) {
    Long memberSeq = AuthMemberUtils.getMemberSeq();
    Question question =
        questionRepository.findById(questionSeq).orElseThrow(() -> new ValidationException("question.not_found"));
    if (!question.getMember().getMemberSeq().equals(memberSeq)) {
      throw new ValidationException("question.unauthorized");
    }
    questionRepository.deleteById(questionSeq);
  }


  /**
   * 문의 등록
   *
   * @param questionEntryRequest
   * @param productSeq
   */
  public void save(QuestionEntryRequest questionEntryRequest, Long productSeq) {
    Long memberSeq = AuthMemberUtils.getMemberSeq();
    Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new ValidationException("member.not_found"));
    Product product = productRepository.findByProductSeqAndValid(productSeq, Valid.TRUE)
                                       .orElseThrow(() -> new ValidationException("product.not_found"));
    Question questionEntity = questionEntryRequest.toQuestionEntity();
    member.addQuestion(questionEntity);
    product.addQuestion(questionEntity);
    questionRepository.save(questionEntity);
  }
}
