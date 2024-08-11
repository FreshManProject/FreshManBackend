package com.freshman.freshmanbackend.global.cloud.repository;

import com.freshman.freshmanbackend.global.cloud.domain.Image;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * S3 이미지 리포지토리
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
