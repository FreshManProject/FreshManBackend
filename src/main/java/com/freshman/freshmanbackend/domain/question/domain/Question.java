package com.freshman.freshmanbackend.domain.question.domain;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 문의 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
public class Question extends BaseTimeEntity {
  @Id
  @Column(name = "QST_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long questionSeq;
  @Column(name = "QST_TITLE", nullable = false)
  private String title;
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

  @OneToMany(fetch = FetchType.LAZY)
  private List<Answer> answers;

  public Question(String title, String content, String type) {
    this.title = title;
    this.content = content;
    this.type = type;
    this.isAnswered = false;
  }
}
