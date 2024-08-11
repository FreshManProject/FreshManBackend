package com.freshman.freshmanbackend.domain.question.domain;

import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 답변 엔티티
 */
@Entity
@NoArgsConstructor
@Getter
@ToString
public class Answer extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ANS_SEQ", nullable = false)
  private Long answerSeq;

  @Column(name = "ANS_CONT", nullable = false)
  private String content;

  public Answer(String content) {
    this.content = content;
  }
}
