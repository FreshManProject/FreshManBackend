package com.freshman.freshmanbackend.domain.member.domain.enums;

import com.freshman.freshmanbackend.global.common.domain.converter.AbstractEnumCodeConverter;
import com.freshman.freshmanbackend.global.common.domain.enums.Codable;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 유저와 어드민을 구분
 */
@Getter
@RequiredArgsConstructor
public enum Role implements Codable {
    USER("ROLE_USER", "U"),
    ADMIN("ROLE_ADMIN", "A");

    private final String desc;
    private final String code;

    public static Role fromCode(final String code) {
        return Codable.fromCode(Role.class, code);
    }

    @Converter
    public static class RoleConverter extends AbstractEnumCodeConverter<Role>{
        @Override
        public Role convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(Role.class, dbData);
        }
    }
}
