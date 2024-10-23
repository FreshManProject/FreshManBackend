package com.freshman.freshmanbackend.domain.product.service.command.like;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.product.dao.ProductLikeDao;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductLike;
import com.freshman.freshmanbackend.domain.product.domain.ProductLikeKey;
import com.freshman.freshmanbackend.domain.product.repository.ProductLikeRepository;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductLikeService {
    private final ProductRepository productRepository;
    private final ProductLikeDao productLikeDao;
    private final ProductLikeRepository productLikeRepository;
    private final MemberRepository memberRepository;
    private final EntityManager entityManager;

    /**
     * 좋아요 누르기
     *
     * @param productSeq
     */
    @Transactional
    public void pushLike(Long productSeq) {
        Product product = productRepository.findById(productSeq)
                .orElseThrow(() -> new ValidationException("product.not_found"));
        Long memberSeq = AuthMemberUtils.getMemberSeq();

        ProductLike pushedLike = productLikeDao.pushedLikeOfMember(memberSeq, productSeq);
        if (pushedLike != null) {
            product.decreaseLikes();
            productLikeRepository.deleteById(pushedLike.getProductLikeKey());
            return;
        }
        Member member = memberRepository.findById(memberSeq)
                .orElseThrow(() -> new ValidationException("member.not_found"));
        product.increaseLikes();
        productLikeRepository.save(new ProductLike(new ProductLikeKey(member, product)));
    }
}
