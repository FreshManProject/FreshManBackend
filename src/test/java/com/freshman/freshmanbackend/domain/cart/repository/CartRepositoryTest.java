package com.freshman.freshmanbackend.domain.cart.repository;

import com.freshman.freshmanbackend.domain.cart.dao.CartDao;
import com.freshman.freshmanbackend.domain.cart.domain.Cart;
import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;
import com.freshman.freshmanbackend.domain.product.repository.ProductCategoryRepository;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

/**
 *
 */
@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartRepositoryTest {
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ProductCategoryRepository productCategoryRepository;
  @Autowired
  private CartDao cartDao;


  @Test
  public void cartSaveTest() {
    Product product = productRepository.findById(1L).orElse(null);
    Member member = memberRepository.findById(9L).orElse(null);
    Cart cart = new Cart(member, product, 10);
    Cart save = cartRepository.save(cart);
    System.out.println(save);
  }

  @Test
  public void productSaveTest() {
    ProductCategory productCategory = productCategoryRepository.findById(1L).orElse(null);
    Product product = new Product("신발", 1000L, "좋은신발", "아디다스", productCategory);
    productRepository.save(product);
  }

}