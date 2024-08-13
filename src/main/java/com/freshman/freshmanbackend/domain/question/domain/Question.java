package com.freshman.freshmanbackend.domain.question.domain;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 문의 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "QUESTION")
public class Question extends BaseTimeEntity {
  @Id
  @Column(name = "QST_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long questionSeq;
  @Column(name = "QST_CONT", nullable = false)
  private String content;
  @Setter
  @Column(name = "QST_IMG")
  private String image;
  @Column(name = "QST_TYPE", nullable = false)
  private String type;
  @Column(name = "QST_RSP", nullable = false)
  private Boolean isAnswered;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MB_SEQ")
  private Member member;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRD_SEQ")
  private Product product;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ANS_SEQ")
  private Answer answer;

  public Question(String content, String type) {
    this.content = content;
    this.type = type;
    this.isAnswered = false;
  }

  public void addAnswer(Answer answer) {
    this.answer = answer;
  }
}
