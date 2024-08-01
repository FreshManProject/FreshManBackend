package com.freshman.freshmanbackend.domain.product.repository;

import com.freshman.freshmanbackend.domain.product.domain.ReviewComment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 후기 댓글 JPA Repository
 *
 * @author 송병선
 */
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {
}
