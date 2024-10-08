package com.freshman.freshmanbackend.domain.member.repository;

import com.freshman.freshmanbackend.domain.member.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
