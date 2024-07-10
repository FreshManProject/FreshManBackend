package com.freshman.freshmanbackend.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 멤버 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member {
    @Id
    @Column(name = "MB_SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    @Column(name = "MB_NM", nullable = false)
    private String name;

    @Column(name = "MB_EMAIL", nullable = false)
    private String email;

    @Column(name = "MB_ADDR", nullable = false)
    private String address;

    @Column(name = "MB_PH_NBR", nullable = false)
    private String phoneNumber;

    @Column(name = "MB_RGST_TYPE", nullable = false)
    private String registrationType;

    @Column(name = "CRE_DTM", nullable = false)
    private LocalDateTime creteDate;

    @Column(name = "UPD_DTM", nullable = false)
    private LocalDateTime updateDate;
}
