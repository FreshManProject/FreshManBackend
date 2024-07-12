package com.freshman.freshmanbackend.domain.member.repository;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 멤버 JPA Repository
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByOauth2Id(String oauth2Id);
    void deleteByOauth2Id(String oauth2Id);
}
