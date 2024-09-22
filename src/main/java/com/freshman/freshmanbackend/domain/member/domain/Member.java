package com.freshman.freshmanbackend.domain.member.domain;

import com.freshman.freshmanbackend.domain.cart.domain.Cart;
import com.freshman.freshmanbackend.domain.member.domain.enums.Role;
import com.freshman.freshmanbackend.domain.member.request.MemberAddressUpdateRequest;
import com.freshman.freshmanbackend.domain.member.request.MemberInfoUpdateRequest;
import com.freshman.freshmanbackend.domain.member.request.MemberUpdateRequest;
import com.freshman.freshmanbackend.domain.question.domain.Question;
import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member extends BaseTimeEntity {
  @Id
  @Column(name = "MB_SEQ")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberSeq;

  @Column(name = "MB_NM")
  private String name;

  @Column(name = "MB_EMAIL")
  private String email;

  @Column(name = "MB_ADDR")
  private String address;

  @Column(name = "MB_ADDR_DTL")
  private String addressDetail;

  @Column(name = "MB_PH_NBR")
  private String phoneNumber;

  @Column(name = "MB_OAUTH_ID", nullable = false)
  private String oauth2Id;

  @Convert(converter = Role.RoleConverter.class)
  @Column(name = "MB_ROLE", nullable = false)
  private Role role;

  @Column(name = "MB_INIT", nullable = false)
  private Boolean init;

  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
  private List<Question> questions;

  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
  private List<Cart> carts;

  public Member(String oauth2Id, Role role) {
    this.oauth2Id = oauth2Id;
    this.role = role;
    this.init = false;
  }

  /**
   * 멤버에게 문의 등록
   *
   * @param question
   */
  public void addQuestion(Question question) {
    questions.add(question);
    question.setMember(this);
  }

  //Oauth로 회원 가입 후 멤버 정보 등록
  public Member registerMember(MemberUpdateRequest registerRequest) {
    this.name = registerRequest.getName();
    this.email = registerRequest.getEmail();
    this.address = registerRequest.getAddress();
    this.addressDetail = registerRequest.getAddressDetail();
    this.phoneNumber = registerRequest.getPhone();
    this.init = true;
    return this;
  }

  /**
   * 멤버 정보 업데이트
   * @param request 멤버 정보
   */
  public void updateInfo(MemberInfoUpdateRequest request){
    this.name = request.getName();
    this.email = request.getEmail();
    this.phoneNumber = request.getPhone();
  }

  /**
   * 멤버 주소 업데이트
   * @param request 멤버 주소
   */
  public void updateAddress(MemberAddressUpdateRequest request){
    this.address = request.getAddress();
    this.addressDetail = request.getAddressDetails();
  }

  /**
   * 멤버 정보 전체 업데이트
   * @param updateRequest
   * @return 멤버
   */
  public Member updateMember(MemberUpdateRequest updateRequest) {
    this.name = updateRequest.getName();
    this.email = updateRequest.getEmail();
    this.address = updateRequest.getAddress();
    this.addressDetail = updateRequest.getAddressDetail();
    this.phoneNumber = updateRequest.getPhone();
    return this;
  }
}
