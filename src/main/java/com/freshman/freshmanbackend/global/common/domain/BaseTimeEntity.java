package com.freshman.freshmanbackend.global.common.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/**
 * 공통 등록/수정일시 엔티티
 *
 * @author 송병선
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

  /**
   * 등록일시
   */
  @CreatedDate
  @Column(name = "CRE_DT", updatable = false, nullable = false)
  protected LocalDateTime createdDt;
  /**
   * 수정일시
   */
  @CreatedDate
  @Column(name = "UPD_DT", nullable = false)
  protected LocalDateTime updatedDt;
}
