package com.example.global.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtTokenUtils {
    private final Key signingKey;
    private final JwtParser jwtParser;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.token.access-expiration-time}")
    private long accessExpirationTime;

    @Value("${jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    // 무효화된 토큰을 저장할 Set
    private final Set<String> invalidatedTokens = new HashSet<>();

    public JwtTokenUtils(
            @Value("${jwt.secret}")
            String jwtSecret,
            RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        // JWT 서명에 사용할 키를 생성
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        // JWT 파서를 생성하여 서명 키로 설정
        this.jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(this.signingKey)
                .build();
    }

    // JWT 가 유효한지 판단하는 메서드
    public boolean validate(String token) {
        try {
            // 암호화된 JWT 를 해석하기 위한 메서드
            jwtParser.parseClaimsJws(token);
            return !invalidatedTokens.contains(token); // 무효화된 토큰 검사 추가 (이미 무효화된것도 처리 됨)
        } catch (Exception e) {
            log.warn("invalid jwt: {}", e.getClass());
            return false;
        }
    }

    // JWT 를 해석해서 사용자 정보를 회수하는 메서드
    public Claims parseClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    // 주어진 사용자 정보를 바탕으로 JWT 를 문자열로 생성하는 메서드
    public String generateAccessToken(UserDetails userDetails) {
        // Claims : JWT 에 담기는 정보의 단위
        Claims jwtClaims = Jwts.claims().setSubject(userDetails.getUsername());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + accessExpirationTime);

        return Jwts.builder()
                .setClaims(jwtClaims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(signingKey)
                .compact();
    }

    // RefreshToken 생성하는 메서드 (redis 에 저장)
    public String generateRefreshToken(UserDetails userDetails) {
        Claims jwtClaims = Jwts.claims().setSubject(userDetails.getUsername());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshExpirationTime);

        String refreshToken = Jwts.builder()
                .setClaims(jwtClaims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(signingKey)
                .compact();

        // redis 에 저장
        redisTemplate.opsForValue().set(
                userDetails.getUsername(),
                refreshToken,
                refreshExpirationTime,
                TimeUnit.MILLISECONDS
        );

        return refreshToken;
    }

    // 로그아웃을 위해 토큰을 무효화하는 메서드
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
        log.info("Token invalidated: {}", token);
    }
}