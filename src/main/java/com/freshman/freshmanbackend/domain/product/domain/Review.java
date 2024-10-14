package com.freshman.freshmanbackend.domain.product.domain;

import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
  /**
   * 댓글 목록
   */
  @Getter
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, orphanRemoval = true,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private final List<ReviewComment> commentList = new ArrayList<>();

  public Review(String content, Byte score, String imagePath, Product product) {
    this.content = content;
    this.score = score;
    this.imagePath = imagePath;
    this.product = product;
  }

  public void registerImage(String imagePath){
    this.imagePath = imagePath;
  }

  /**
   * 후기 정보 수정
   */
  public void update(String content, Byte score) {
    this.content = content;
    this.score = score;
  }
}
