package com.freshman.freshmanbackend.domain.member.repository;

import com.freshman.freshmanbackend.domain.member.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 멤버 JPA Repository
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
  void deleteByOauth2Id(String oauth2Id);

  Member findByOauth2Id(String oauth2Id);

  Optional<Member> findByEmail(String email);
}
