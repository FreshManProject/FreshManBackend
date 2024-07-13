package com.freshman.freshmanbackend.global.auth.util;

import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 유틸 클래스
 */
@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getOauth2Id(String token) {
        String oauth2Id;
        try{
            oauth2Id = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("oauth2Id", String.class);
        }catch (Exception e){
            throw new ValidationException("auth.invalid_refresh_token");
        }
        return oauth2Id;
    }

    public String getRole(String token) {
        String role;
        try{
             role = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
        }catch (Exception e){
            throw new ValidationException("auth.invalid_refresh_token");
        }
        return role;
    }

    public Boolean isExpired(String token) {
        boolean before;
        try{
            before = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        }catch (Exception e){
            throw new ValidationException("auth.invalid_refresh_token");
        }
        return before;
    }

    public String createJwt(String category,String username, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("oauth2Id", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String getCategory(String token) {
        String category;
        try{
            category = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
        }catch (Exception e){
            throw new ValidationException("auth.invalid_refresh_token");
        }
        return category;
    }
}
