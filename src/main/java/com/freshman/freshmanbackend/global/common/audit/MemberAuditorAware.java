package com.freshman.freshmanbackend.global.common.audit;

import com.freshman.freshmanbackend.global.auth.dto.CustomOauth2User;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 등록자 정보
 *
 * @author 송병선
 */
@Component
public class MemberAuditorAware implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return Optional.empty();
    }

    if (authentication.getPrincipal() instanceof CustomOauth2User oauth2User) {
      return Optional.of(oauth2User.getMemberSeq());
    }
    return Optional.empty();
  }
}
