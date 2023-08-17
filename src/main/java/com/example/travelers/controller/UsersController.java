package com.example.travelers.controller;

import com.example.travelers.dto.LoginRequestDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.RegisterRequestDto;
import com.example.travelers.jwt.JwtTokenDto;
import com.example.travelers.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    // 로그인 endpoint
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        JwtTokenDto jwtTokenDto = usersService.loginUser(loginRequestDto);
        return ResponseEntity.ok(jwtTokenDto);
    }

    // 회원가입 endpoint
    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> register(
            @RequestBody RegisterRequestDto registerRequestDto
    ) {
        MessageResponseDto responseDto = usersService.registerUser(registerRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 프로필 이미지 업데이트 엔드포인트
    @PutMapping(value = "/update-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> userUpdateImage(
            @RequestParam(value = "image") MultipartFile multipartFile
    ) {
        // 이미지 업로드 서비스 호출
        MessageResponseDto responseDto = usersService.uploadProfileImage(multipartFile);

        return ResponseEntity.ok(responseDto);
    }

    // TODO 사용자 정보 수정 PUT 엔드포인트
    // 수정할 수 있는 정보는 어떤거로? password, mbti

    // TODO 사용자 삭제 DELETE 엔드포인트

}
