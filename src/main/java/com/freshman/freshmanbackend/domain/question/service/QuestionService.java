package com.freshman.freshmanbackend.domain.question.service;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;
import com.freshman.freshmanbackend.domain.question.domain.Question;
import com.freshman.freshmanbackend.domain.question.repository.QuestionRepository;
import com.freshman.freshmanbackend.domain.question.request.QuestionEntryRequest;
import com.freshman.freshmanbackend.domain.question.response.MyQuestionResponse;
import com.freshman.freshmanbackend.domain.question.response.ProductQuestionResponse;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.cloud.service.S3UploadService;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 질문 서비스
 */
@Service
@RequiredArgsConstructor
public class QuestionService {
  private static final String QUESTION_FOLDER = "question-image/";
  private final QuestionRepository questionRepository;
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;
  private final S3UploadService s3UploadService;

  /**
   * 문의 삭제
   *
   * @param questionSeq
   */
  @Transactional
  public void delete(Long questionSeq) {
    Long memberSeq = AuthMemberUtils.getMemberSeq();
    Question question =
        questionRepository.findById(questionSeq).orElseThrow(() -> new ValidationException("question.not_found"));
    if (!question.getMember().getMemberSeq().equals(memberSeq)) {
      throw new ValidationException("question.unauthorized");
    }
    questionRepository.deleteById(questionSeq);
  }

  @Transactional(readOnly = true)
  public List<MyQuestionResponse> getMyQuestion(int page) {
    Long memberSeq = AuthMemberUtils.getMemberSeq();
    PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
    Page<Question> questions = questionRepository.findByMember_MemberSeq(memberSeq, pageRequest);
    return questions.getContent().stream().map(MyQuestionResponse::of).toList();
  }

  /**
   * 상품에 대한 문의 가져오기
   *
   * @param productSeq
   * @param page
   * @return 페이지에 해당하는 문의 리스트
   */
  @Transactional(readOnly = true)
  public List<ProductQuestionResponse> getProductQuestion(Long productSeq, int page) {
    PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
    Page<Question> questions = questionRepository.findByProduct_ProductSeq(productSeq, pageRequest);
    return questions.getContent().stream().map(ProductQuestionResponse::fromQuestion).toList();
  }

  /**
   * 문의 등록
   *
   * @param questionEntryRequest
   * @param productSeq
   */
  @Transactional
  public void save(QuestionEntryRequest questionEntryRequest, Long productSeq) {
    Long memberSeq = AuthMemberUtils.getMemberSeq();
    Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new ValidationException("member.not_found"));
    Product product = productRepository.findByProductSeqAndValid(productSeq, true)
                                       .orElseThrow(() -> new ValidationException("product.not_found"));
    Question questionEntity = questionEntryRequest.toQuestionEntity();
    member.addQuestion(questionEntity);
    product.addQuestion(questionEntity);
    try {
      String url = s3UploadService.saveFile(questionEntryRequest.getImage(), QUESTION_FOLDER + questionEntity.getQuestionSeq());
      questionEntity.setImage(url);
    } catch (IOException e) {
      throw new ValidationException("s3.save_failed");
    }
    questionRepository.save(questionEntity);
  }
}
