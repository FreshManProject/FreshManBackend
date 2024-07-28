package com.freshman.freshmanbackend.domain.question.controller;

import com.freshman.freshmanbackend.domain.question.request.QuestionEntryRequest;
import com.freshman.freshmanbackend.domain.question.service.QuestionService;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

/**
 * 문의 컨트롤러
 */
@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
  private QuestionService questionService;

  /**
   * 제품에 대한 문의 등록
   *
   * @param productSeq
   * @return 성공여부
   */
  @PostMapping("/product/{productSeq}")
  public ResponseEntity<?> create(@PathVariable Long productSeq,
      @RequestBody QuestionEntryRequest questionEntryRequest) {
    questionService.save(questionEntryRequest, productSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 문의 삭제하기
   *
   * @param questionSeq
   * @return 성공여부
   */
  @DeleteMapping("/{questionSeq}")
  public ResponseEntity<?> delete(@PathVariable Long questionSeq) {
    questionService.delete(questionSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 제품에 대한 문의 가져오기
   *
   * @return 제품에 대한 문의 리스트
   */
  @GetMapping("/products/{productSeq}")
  public ResponseEntity<?> get(@PathVariable Long productSeq, @RequestParam int page) {
    //TODO: 제품 문의 리스트 가져오기
    return ResponseEntity.ok().build();
  }

  /**
   * 내 문의 불러오기
   *
   * @return 내 문의 리스트
   */
  @GetMapping("/my-questions")
  public ResponseEntity<?> getMyQuestions(@RequestParam int page) {
    //TODO: 내 문의 리스트 가져오기
    return ResponseEntity.ok().build();
  }
}
