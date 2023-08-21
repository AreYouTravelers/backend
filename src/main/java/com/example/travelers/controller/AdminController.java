package com.example.travelers.controller;

import com.example.travelers.dto.DeleteUserByAdminDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.UserProfileDto;
import com.example.travelers.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UsersService usersService;


    // 회원 정보 리스트 조회 (관리자용) endpoint
    @GetMapping("/profile-list")
    public ResponseEntity<Page<UserProfileDto>> getProfileList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // 페이지 번호를 1부터 시작하도록 조정
        page = Math.max(1, page);

        Page<UserProfileDto> userList = usersService.getProfileList(page - 1, size);
        return ResponseEntity.ok(userList);
    }

    // 관리자에 의한 사용자 탈퇴 처리 ( role = 관리자 ) endpoint
    @DeleteMapping("/deactivate")
    public ResponseEntity<MessageResponseDto> deleteUserByAdmin(
            @RequestBody DeleteUserByAdminDto deleteUserByAdminDto
    ) {
        MessageResponseDto responseDto = usersService.deleteUserByAdmin(deleteUserByAdminDto);
        return ResponseEntity.ok(responseDto);
    }
}
