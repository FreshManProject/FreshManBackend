package com.freshman.freshmanbackend.domain.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 어드민 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "ADMIN")
public class Admin {
    @Id
    @Column(name = "ADM_SEQ")
    private Long id;
    @Column(name = "ADM_PWD")
    private String password;
}
