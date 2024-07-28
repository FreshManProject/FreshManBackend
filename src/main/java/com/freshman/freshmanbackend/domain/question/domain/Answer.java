package com.freshman.freshmanbackend.domain.question.domain;

import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 답변 엔티티
 */
@Entity
public class Answer extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ANS_SEQ", nullable = false)
  private Long answerSeq;

  @Column(name = "ANS_TITLE", nullable = false)
  private String title;

  @Column(name = "ANS_CONT", nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "QST_SEQ")
  private Question question;
}
