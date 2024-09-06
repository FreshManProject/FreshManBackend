package com.freshman.freshmanbackend.domain.cart.service;

import com.freshman.freshmanbackend.domain.cart.dao.CartDao;
import com.freshman.freshmanbackend.domain.cart.dao.CartListDao;
import com.freshman.freshmanbackend.domain.cart.domain.Cart;
import com.freshman.freshmanbackend.domain.cart.repository.CartRepository;
import com.freshman.freshmanbackend.domain.cart.request.CartEntryRequest;
import com.freshman.freshmanbackend.domain.cart.request.CartUpdateRequest;
import com.freshman.freshmanbackend.domain.cart.response.CartInfoResponse;
import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

/**
 * 장바구니 서비스
 */
@Service
@RequiredArgsConstructor
public class CartService {
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final MemberRepository memberRepository;
  private final CartListDao cartListDao;
  private final CartDao cartDao;

  @Transactional
  public void add(CartEntryRequest cartEntryRequest) {
    Long productSeq = cartEntryRequest.getProductSeq();
    Integer quantity = cartEntryRequest.getQuantity();
    Long currentMemberSeq = AuthMemberUtils.getMemberSeq();
    Cart cartByMemberIdAndProductId = cartDao.getCartByMemberIdAndProductId(currentMemberSeq, productSeq);
    if (cartByMemberIdAndProductId == null) {
      Product product =
          productRepository.findById(productSeq).orElseThrow(() -> new ValidationException("product.not_found"));
      Member member =
          memberRepository.findById(currentMemberSeq).orElseThrow(() -> new ValidationException("member.not_found"));
      Cart cart = new Cart(member, product, quantity);
      cartRepository.save(cart);
      return;
    }
    cartByMemberIdAndProductId.updateCartQuantity(cartByMemberIdAndProductId.getQuantity() + quantity);
  }

  @Transactional
  public void delete(Long cartId) {
    Cart cart = getCart(cartId);
    if (!cart.getMember().getOauth2Id().equals(AuthMemberUtils.getUserOauth2Id())) {
      throw new ValidationException("cart.unauthorized");
    }
    cartRepository.deleteById(cartId);
  }

  @Transactional(readOnly = true)
  public List<CartInfoResponse> getUserCartsList(int page) {
    Long currentMemberSeq = AuthMemberUtils.getMemberSeq();
    PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC));
    Page<Cart> carts = cartRepository.findAll(pageRequest);
//    List<Cart> carts = cartListDao.getByMemberSeq(currentMemberSeq);
    return carts.stream().map(CartInfoResponse::fromCart).collect(Collectors.toList());
  }

  @Transactional
  public void update(CartUpdateRequest cartUpdateRequest, Long cartSeq) {
    Cart cart = getCart(cartSeq);
    if (!cart.getMember().getOauth2Id().equals(AuthMemberUtils.getUserOauth2Id())) {
      throw new ValidationException("cart.unauthorized");
    }
    cart.updateCartQuantity(cartUpdateRequest.getQuantity());
  }

  private Cart getCart(Long cartId) {
    return cartRepository.findById(cartId).orElseThrow(() -> new ValidationException("cart.id_not_found"));
  }
}
