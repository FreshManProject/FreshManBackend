package com.freshman.freshmanbackend.domain.product.domain;

import com.freshman.freshmanbackend.domain.product.domain.enums.ReviewType;
import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 후기 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
@Table(name = "REVIEW")
public class Review extends BaseTimeEntity {

  /**
   * 후기 일련번호
   */
  @Id
  @Column(name = "RVW_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewSeq;
  /**
   * 후기 내용
   */
  @Column(name = "RVW_CTT", nullable = false)
  private String content;
  /**
   * 별점
   */
  @Column(name = "RVW_SCR", nullable = false)
  private Byte score;
  /**
   * 후기 타입
   */
  @Convert(converter = ReviewType.TypeCodeConverter.class)
  @Column(name = "RVW_TYP", nullable = false)
  private ReviewType type;
  /**
   * 이미지 경로
   */
  @Column(name = "RVW_IMG_PATH")
  private String imagePath;
  /**
   * 후기 승인여부
   */
  @Column(name = "RVW_APRV_YN", nullable = false)
  private Boolean approvalYn = Boolean.FALSE;
  /**
   * 회원 일련번호
   */
  @CreatedBy
  @Column(name = "MB_SEQ", updatable = false, nullable = false)
  private Long memberSeq;
  /**
   * 상품
   */
  @ManyToOne
  @JoinColumn(name = "PRD_SEQ")
  private Product product;

  public Review(String content, Byte score, ReviewType type, String imagePath, Product product) {
    this.content = content;
    this.score = score;
    this.type = type;
    this.imagePath = imagePath;
    this.product = product;
  }

  /**
   * 후기 정보 수정
   */
  public void update(String content, Byte score) {
    this.content = content;
    this.score = score;
  }
}
