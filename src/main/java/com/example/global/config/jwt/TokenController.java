package com.example.global.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokenReissueService tokenReissueService;

    @Autowired
    public TokenController(TokenReissueService tokenReissueService) {
        this.tokenReissueService = tokenReissueService;
    }

    @PostMapping("/reissue")
    public ResponseEntity<JwtTokenDto> reissueToken(
            @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        // refreshToken 을 사용하여 새로운 accessToken 을 발급합니다.
        JwtTokenDto tokenDto = tokenReissueService.reissueAccessToken(refreshTokenDto);

        log.info(tokenDto.getAccessToken());
        log.info(tokenDto.getRefreshToken());

        return ResponseEntity.ok(tokenDto);
    }

}
