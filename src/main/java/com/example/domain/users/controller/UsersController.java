package com.example.domain.users.controller;

import com.example.domain.jwt.JwtTokenDto;
import com.example.domain.users.dto.*;
import com.example.domain.users.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    // 로그아웃 endpoint
    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(
            HttpServletRequest request
    ) {
        MessageResponseDto responseDto = usersService.logoutUser(request);
        return ResponseEntity.ok(responseDto);
    }

    // 회원가입 endpoint
    @PostMapping
    public ResponseEntity<MessageResponseDto> register(
            @RequestBody RegisterRequestDto registerRequestDto
    ) {
        MessageResponseDto responseDto = usersService.registerUser(registerRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 회원 정보 조회 (본인) endpoint
    // userId 가 아닌 본인 Jwt 를 사용해서 조회
    @GetMapping("/my-profile")
    public ResponseEntity<UserProfileDto> getMyProfile() {
        UserProfileDto user = usersService.getMyProfile();
        return ResponseEntity.ok(user);
    }

    // 프로필 이미지 업데이트 endpoint
    @PatchMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> userUpdateImage(
            @RequestParam(value = "image") MultipartFile multipartFile
    ) {
        // 이미지 업로드 서비스 호출
        MessageResponseDto responseDto = usersService.uploadProfileImage(multipartFile);

        return ResponseEntity.ok(responseDto);
    }

    // 사용자 정보 Password 수정 endpoint
    @PatchMapping("/password")
    public ResponseEntity<MessageResponseDto> updatePassword(
            @RequestBody UpdatePasswordDto updatePasswordDto
    ) {
        MessageResponseDto responseDto = usersService.updatePassword(updatePasswordDto);
        return ResponseEntity.ok(responseDto);
    }

    // 사용자 정보 email 수정 endpoint
    @PatchMapping("/email")
    public ResponseEntity<MessageResponseDto> updateEmail(
            @Valid @RequestBody UpdateEmailDto updateEmailDto
    ) {
        MessageResponseDto responseDto = usersService.updateEmail(updateEmailDto);
        return ResponseEntity.ok(responseDto);
    }

    // 사용자 정보 mbti 수정 endpoint
    @PatchMapping("/mbti")
    public ResponseEntity<MessageResponseDto> updateMbti(
            @Valid @RequestBody UpdateMbtiDto updateMbtiDto
    ) {
        MessageResponseDto responseDto = usersService.updateMbti(updateMbtiDto);
        return ResponseEntity.ok(responseDto);
    }

    // 사용자 탈퇴 endpoint
    @DeleteMapping("/deactivate")
    public ResponseEntity<MessageResponseDto> deleteUser(
            HttpServletRequest request,
            @RequestBody DeleteUserDto deleteUserDto
    ) {
        MessageResponseDto responseDto = usersService.deleteUser(request, deleteUserDto);
        return ResponseEntity.ok(responseDto);
    }
}
