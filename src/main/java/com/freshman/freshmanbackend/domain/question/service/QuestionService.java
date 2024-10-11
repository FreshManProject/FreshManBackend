package com.freshman.freshmanbackend.domain.question.service;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;
import com.freshman.freshmanbackend.domain.question.dao.QuestionDao;
import com.freshman.freshmanbackend.domain.question.domain.Question;
import com.freshman.freshmanbackend.domain.question.repository.QuestionRepository;
import com.freshman.freshmanbackend.domain.question.request.QuestionEntryRequest;
import com.freshman.freshmanbackend.domain.question.response.MyQuestionResponse;
import com.freshman.freshmanbackend.domain.question.response.ProductQuestionResponse;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.cloud.service.S3UploadService;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import com.freshman.freshmanbackend.global.common.response.NoOffsetPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

/**
 * 질문 서비스
 */
@Service
@RequiredArgsConstructor
public class QuestionService {
  private static final int PAGE_SIZE = 10;
  private static final String QUESTION_FOLDER = "question-image/";
  private final QuestionRepository questionRepository;
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;
  private final S3UploadService s3UploadService;
  private final QuestionDao questionDao;

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

  /**
   * 내 문의 가져오기
   * @param page
   * @return
   */
  @Transactional(readOnly = true)
  public NoOffsetPageResponse getMyQuestion(int page) {
    Boolean isEnd = false;
    String name = AuthMemberUtils.getOauthUserDto().getName();
    Long memberSeq = AuthMemberUtils.getMemberSeq();

    List<MyQuestionResponse> myQuestion = questionDao.getMyQuestion(memberSeq, PAGE_SIZE, page);
    if (myQuestion.size() != PAGE_SIZE + 1){
      isEnd = true;
    }
    else{
      myQuestion.remove(PAGE_SIZE);
    }
    myQuestion.stream().forEach(qustion -> qustion.setMemberName(name));
    return new NoOffsetPageResponse(myQuestion, isEnd);
  }

  /**
   * 상품에 대한 문의 가져오기
   *
   * @param productSeq
   * @param page
   * @return 페이지에 해당하는 문의 리스트
   */
  @Transactional(readOnly = true)
  public NoOffsetPageResponse getProductQuestion(Long productSeq, int page) {
    Boolean isEnd = false;
    List<ProductQuestionResponse> productQuestion = questionDao.getProductQuestion(productSeq, PAGE_SIZE, page);
    if (productQuestion.size() != PAGE_SIZE + 1) {
      isEnd = true;
    }
    else{
      productQuestion.remove(PAGE_SIZE);
    }
    return new NoOffsetPageResponse(productQuestion, isEnd);
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
