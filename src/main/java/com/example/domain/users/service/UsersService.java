package com.example.domain.users.service;

import com.example.domain.users.domain.UsersRole;
import com.example.domain.users.dto.*;
import com.example.domain.users.domain.Users;
import com.example.global.config.jwt.JwtTokenDto;
import com.example.global.config.jwt.JwtTokenUtils;
import com.example.domain.users.repository.UsersRepository;
import com.example.domain.users.dto.CustomUserDetails;
import com.example.global.config.s3.S3Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final JpaUserDetailsManager manager;
    private final AuthService authService;
    private final S3Service s3Service;

    // 로그인 기능
    public JwtTokenDto loginUser(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();

        if (!manager.userExists(username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "올바른 Username 이 아닙니다");
        }

        UserDetails userDetails = manager.loadUserByUsername(username);

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password 가 일치하지 않습니다");
        }

        JwtTokenDto tokenDto = new JwtTokenDto();
        tokenDto.setAccessToken(jwtTokenUtils.generateAccessToken(userDetails));
        tokenDto.setRefreshToken(jwtTokenUtils.generateRefreshToken(userDetails));

        return tokenDto;
    }

    // 로그아웃 기능
    public MessageResponseDto logoutUser(HttpServletRequest request) {
        // 사용자 token 추출
        String token = authService.extractTokenFromHeader(request.getHeader(HttpHeaders.AUTHORIZATION));

        // 토큰 무효화
        jwtTokenUtils.invalidateToken(token);

        return new MessageResponseDto("로그아웃 되었습니다.");
    }

    // 회원가입 기능
    public MessageResponseDto registerUser(RegisterRequestDto registerRequestDto) {
        String username = registerRequestDto.getUsername();
        String password = registerRequestDto.getPassword();

        // 입력한 패스워드와 패스워드 확인이 일치하지 않으면 예외 발생
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getPasswordCheck())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "패스워드와 패스워드 확인이 일치하지 않습니다");
        }

        CustomUserDetails user = CustomUserDetails.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(registerRequestDto.getEmail())
                .mbti(registerRequestDto.getMbti())
                .gender(registerRequestDto.getGender())
                .fullName(registerRequestDto.getFullName())
                .birthDate(registerRequestDto.getBirthDate())
                .temperature(36.5)
                .role(UsersRole.MEMBER)
                .createdAt(LocalDateTime.now())  // 생성일 설정 부분
                .build();

        manager.createUser(user);

        return new MessageResponseDto("회원가입을 성공했습니다.");
    }

    // 회원 정보 (본인 단일) 조회 기능
    public UserProfileDto getMyProfile() {
        Users usersEntity = authService.getUser();
        return UserProfileDto.fromEntity(usersEntity);
    }

    // 회원 정보 리스트 조회 (관리자용)
    public Page<UserProfileDto> getProfileList(int page, int size) {
        // 관리자 인지 검증
        log.info("서비스 실행 시작");
        Users currentUser = authService.getUser();
        UsersRole currentUserRole = currentUser.getRole();
        log.info("현재 로그인한 유저 권한 받아옴");

        if (!currentUserRole.equals(UsersRole.ADMIN))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자만 사용 가능합니다.");
        log.info("ADMIN 권한 비교 완료");

        // 회원, 가이드만 조회되도록 리스트 추가
        List<UsersRole> allowedRolesList = Arrays.asList(UsersRole.MEMBER, UsersRole.GUIDE);

        Pageable pageable = PageRequest.of(page, size,
                Sort.by("id")
        );
        return usersRepository.findAllByDeletedAtIsNullAndRoleIn(allowedRolesList, pageable)
                .map(UserProfileDto::fromEntity);
    }

    // 프로필 이미지 업로드 기능
    public MessageResponseDto uploadProfileImage(MultipartFile multipartFile) {
        Users userEntity = authService.getUser();
        String imageUrl;

        // 파일 경로 생성
        String key = String.format("profile/%d/%s", userEntity.getId(), multipartFile.getOriginalFilename());

        try {
            // 파일 업로드 및 URL 생성
            imageUrl = s3Service.uploadFile(key, multipartFile);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload image", e);
        }

        // 데이터베이스에 URL 저장
        userEntity.setProfileImg(imageUrl);
        usersRepository.save(userEntity);

        // 응답 반환
        return new MessageResponseDto("Image uploaded successfully");
    }

    // 사용자 정보 Password 수정 PUT 엔드포인트
    public MessageResponseDto updatePassword(UpdatePasswordDto updatePasswordDto) {
        Users currentUser = authService.getUser();

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(updatePasswordDto.getCurrentPassword(), currentUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다.");
        }

        // 변경할 비밀번호 확인
        if (!updatePasswordDto.getChangePassword().equals(updatePasswordDto.getChangePasswordCheck())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 변경할 비밀번호
        String encodedPassword = passwordEncoder.encode(updatePasswordDto.getChangePassword());

        currentUser.setPassword(encodedPassword);
        usersRepository.save(currentUser);

        return new MessageResponseDto("비밀번호 변경이 완료되었습니다.");
    }

    // 사용자 정보 email 수정 PUT 엔드포인트
    public MessageResponseDto updateEmail(UpdateEmailDto updateEmailDto) {
        Users currentUser = authService.getUser();

        currentUser.setEmail(updateEmailDto.getEmail());
        usersRepository.save(currentUser);

        return new MessageResponseDto("이메일 변경이 완료되었습니다.");
    }

    // 사용자 정보 mbti 수정 PUT 엔드포인트
    public MessageResponseDto updateMbti(UpdateMbtiDto updateMbtiDto) {
        Users currentUser = authService.getUser();

        currentUser.setMbti(updateMbtiDto.getMbti());
        usersRepository.save(currentUser);

        return new MessageResponseDto("MBTI 변경이 완료되었습니다.");
    }

    // 사용자 탈퇴 DELETE 엔드포인트
    public MessageResponseDto deleteUser(HttpServletRequest request, DeleteUserDto deleteUserDto) {
        Users currentUser = authService.getUser();

        // 비밀번호 확인
        if (!passwordEncoder.matches(deleteUserDto.getPassword(), currentUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 이미 삭제된 사용자인 경우 Bad Request 반환
        if (currentUser.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 삭제된 사용자입니다.");
        }

        // 토큰 무효화
        String token = authService.extractTokenFromHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        jwtTokenUtils.invalidateToken(token);

        // 사용자 삭제 처리 (소프트 delete)
        usersRepository.delete(currentUser);

        return new MessageResponseDto("사용자가 성공적으로 삭제되었습니다.");
    }

    // 관리자에 의한 사용자 탈퇴 처리 endpoint
    public MessageResponseDto deleteUserByAdmin(DeleteUserByAdminDto deleteUserByAdminDto) {
        // 관리자 인지 검증
        Users currentUser = authService.getUser();
        UsersRole currentUserRole = currentUser.getRole();

        if (!currentUserRole.equals(UsersRole.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자만 사용 가능합니다.");
        }

        Optional<Users> targetEntity = usersRepository.findByUsername(deleteUserByAdminDto.getUsername());
        if (targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제하려는 User가 존재하지 않습니다.");
        }

        Users target = targetEntity.get();

        // 이미 삭제된 사용자인 경우 Bad Request 반환
        if (target.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 삭제된 사용자입니다.");
        }

//        target.setDeletedAt(LocalDateTime.now());
//        usersRepository.save(target);
        usersRepository.delete(target);

        return new MessageResponseDto("사용자가 성공적으로 삭제되었습니다.");
    }
}
