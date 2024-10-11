package com.freshman.freshmanbackend.domain.question.controller;

import com.freshman.freshmanbackend.domain.question.controller.validator.AnswerValidator;
import com.freshman.freshmanbackend.domain.question.request.AnswerEntryRequest;
import com.freshman.freshmanbackend.domain.question.response.AnswerResponse;
import com.freshman.freshmanbackend.domain.question.service.AnswerService;
import com.freshman.freshmanbackend.global.common.response.DataResponse;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 답변 컨트롤러
 */
@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {
  private final AnswerService answerService;

  @GetMapping("/{questionSeq}")
  public ResponseEntity<?> get(@PathVariable(name = "questionSeq") Long questionSeq) {
    AnswerResponse answer = answerService.getByQuestionSeq(questionSeq);
    return ResponseEntity.ok(new DataResponse(answer));
  }

  @PostMapping("/{questionSeq}")
  public ResponseEntity<?> post(@PathVariable(name = "questionSeq") Long questionSeq,
      @RequestBody AnswerEntryRequest answerEntryRequest) {
    AnswerValidator.validate(answerEntryRequest);
    answerService.save(answerEntryRequest, questionSeq);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
