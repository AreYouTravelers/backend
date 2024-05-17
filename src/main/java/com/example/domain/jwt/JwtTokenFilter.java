package com.example.domain.jwt;

import com.example.domain.users.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    // OncePerRequestFilter : 요청이 들어올 때마다 한 번만 실행
    private final JwtTokenUtils jwtTokenUtils;

    public JwtTokenFilter(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 헤더에서 인증 토큰을 추출
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 실제 JWT 토큰만 추출
            String token = authHeader.split(" ")[1];
            // JWT 유효성 검사
            if (jwtTokenUtils.validate(token)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                // JWT 에서 사용자 이름을 가져옴
                String username = jwtTokenUtils
                        .parseClaims(token)
                        .getSubject();
                // 인증 토큰 생성
                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        CustomUserDetails.builder()
                                .username(username)
                                .build(), token, new ArrayList<>()
                );
                // SecurityContext 에 사용자 정보 설정
                context.setAuthentication(authenticationToken);
                // SecurityContextHolder 에 SecurityContext 설정
                SecurityContextHolder.setContext(context);
                log.info("JWT 로 인증 정보 설정 완료");
            } else {
                log.warn("JWT 유효성 검사 실패");
            }
        }
        // 다음 필터 또는 요청 핸들러로 이동
        filterChain.doFilter(request, response);
    }
}