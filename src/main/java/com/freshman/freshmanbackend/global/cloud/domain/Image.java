package com.freshman.freshmanbackend.global.cloud.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

/**
 * 이미지 공통 엔티티
 */
@Entity
@NoArgsConstructor
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "IMG_ID")
  private Long id;
  @Column(name = "IMG_URL")
  private String url;

  public Image(String url) {
    this.url = url;
  }
}
