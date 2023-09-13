package com.example.travelers.service;

import com.example.travelers.dao.RedisDao;
import com.example.travelers.jwt.JwtTokenDto;
import com.example.travelers.jwt.JwtTokenUtils;
import com.example.travelers.jwt.RefreshTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class TokenReissueService {
    private final JpaUserDetailsManager manager;
    private final JwtTokenUtils jwtTokenUtils;
    private final RedisDao redisDao; // RedisDao 추가

    @Autowired
    public TokenReissueService(JpaUserDetailsManager manager, JwtTokenUtils jwtTokenUtils, RedisDao redisDao) {
        this.manager = manager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.redisDao = redisDao; // RedisDao 주입
    }

    public JwtTokenDto reissueAccessToken(RefreshTokenDto tokenDto) {
        // refreshToken 을 사용하여 username 을 가져옴
        String username = getUsernameFromRefreshToken(tokenDto.getRefreshToken());
//        log.info(username);

        if (username != null) {
            UserDetails userDetails = manager.loadUserByUsername(username);

            // 사용자가 존재하고 refreshToken 이 유효한 경우
            if (userDetails != null && jwtTokenUtils.validate(tokenDto.getRefreshToken())) {
                // 새로운 accessToken 발급
                String newAccessToken = jwtTokenUtils.generateAccessToken(userDetails);
//                log.info("newAccessToken : {}", newAccessToken);

                // Redis 에서 refreshToken 을 가져와서 비교
                String storedRefreshToken = redisDao.getValues(username);
//                log.info("redisRefreshToken : {}", storedRefreshToken);


                if (storedRefreshToken != null && storedRefreshToken.equals(tokenDto.getRefreshToken())) {
                    // 저장된 refreshToken 과 요청한 refreshToken 이 일치하면
                    // 새로운 refreshToken 을 발급하고 Redis 에 저장
                    String newRefreshToken = jwtTokenUtils.generateRefreshToken(userDetails);
                    redisDao.setValues(username, newRefreshToken);
                    JwtTokenDto jwtTokenDto = new JwtTokenDto();
                    jwtTokenDto.setAccessToken(newAccessToken);
                    jwtTokenDto.setRefreshToken(newRefreshToken);

                    return jwtTokenDto;
                } else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "저장된 리프레쉬토큰이 없거나 요청된 토큰과 일치하지않습니다.");
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자가 존재하지 않거나 refreshToken 이 검증되지 않았습니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.");
    }

    private String getUsernameFromRefreshToken(String refreshToken) {
        try {
            return jwtTokenUtils.parseClaims(refreshToken).getSubject();
        } catch (Exception e) {
            log.error("Failed to parse refreshToken: {}", e.getMessage());
            return null; // refreshToken 파싱 실패
        }
    }
}

