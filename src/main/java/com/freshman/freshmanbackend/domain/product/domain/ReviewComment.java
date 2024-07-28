package com.freshman.freshmanbackend.domain.product.domain;

import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import org.springframework.data.annotation.CreatedBy;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 후기 댓글 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "REVIEW_COMMENT")
public class ReviewComment extends BaseTimeEntity {

  /**
   * 후기 댓글 일련번호
   */
  @Id
  @Column(name = "RVW_CMNT_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentSeq;
  /**
   * 댓글 내용
   */
  @Column(name = "RVW_CMNT_CTT", nullable = false)
  private String content;
  /**
   * 후기
   */
  @ManyToOne
  @JoinColumn(name = "RVW_SEQ", nullable = false)
  private Review review;
  /**
   * 상위 댓글
   */
  @ManyToOne
  @JoinColumn(name = "PRNT_RVW_CMNT_SEQ")
  private ReviewComment parent;
  /**
   * 회원 일련번호
   */
  @CreatedBy
  @Column(name = "MB_SEQ", updatable = false, nullable = false)
  private Long memberSeq;
  /**
   * 답글 목록
   */
  @Getter
  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private final List<ReviewComment> replyList = new ArrayList<>();

  public ReviewComment(String content, Review review, ReviewComment parent) {
    this.content = content;
    this.review = review;
    this.parent = parent;
  }

  public void update(String content) {
    this.content = content;
  }
}
