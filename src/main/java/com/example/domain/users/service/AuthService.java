package com.example.domain.users.service;

import com.example.domain.users.domain.Users;
import com.example.domain.jwt.JwtTokenUtils;
import com.example.domain.users.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final HttpServletRequest request;
    private final JwtTokenUtils jwtTokenUtils;
    private final UsersRepository usersRepository;

    public AuthService(HttpServletRequest request, JwtTokenUtils jwtTokenUtils, UsersRepository usersRepository) {
        this.request = request;
        this.jwtTokenUtils = jwtTokenUtils;
        this.usersRepository = usersRepository;
    }

    // 현재 로그인한 사용자 정보 가져오기
    public Users getUser() {
        log.info("Authorization Header: {}", request.getHeader(HttpHeaders.AUTHORIZATION));
        // Authorization 헤더에서 토큰 추출
        String token = extractTokenFromHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (!jwtTokenUtils.validate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "validate");
        }

        // 토큰을 해석하여 사용자명 추출
        String username = jwtTokenUtils.parseClaims(token).getSubject();
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // 헤더에서 토큰 추출
    public String extractTokenFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "extract");
        }
        return authHeader.split(" ")[1];
    }
}
