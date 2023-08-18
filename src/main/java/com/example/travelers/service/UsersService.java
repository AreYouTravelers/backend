package com.example.travelers.service;

import com.example.travelers.dto.LoginRequestDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.RegisterRequestDto;
import com.example.travelers.dto.UserProfileDto;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.jwt.JwtTokenDto;
import com.example.travelers.jwt.JwtTokenUtils;
import com.example.travelers.repos.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final JpaUserDetailsManager manager;
    private final AuthService authService;

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
        tokenDto.setToken(jwtTokenUtils.generateToken(userDetails));
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
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .birthDate(registerRequestDto.getBirthDate())
                .createdAt(LocalDateTime.now())  // 생성일 설정 부분
                .build();

        manager.createUser(user);

        return new MessageResponseDto("회원가입을 성공했습니다.");
    }

    // 회원 정보 (본인 단일) 조회 기능
    public UserProfileDto getMyProfile() {
        UsersEntity usersEntity = authService.getUser();
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername(usersEntity.getUsername());
        userProfileDto.setEmail(usersEntity.getEmail());
        userProfileDto.setProfileImg(usersEntity.getProfileImg());
        userProfileDto.setMbti(usersEntity.getMbti());
        userProfileDto.setGender(usersEntity.getGender());
        userProfileDto.setRole(usersEntity.getRole());
        userProfileDto.setFullName(usersEntity.getFirstName() + usersEntity.getLastName());
        userProfileDto.setTemperature(usersEntity.getTemperature());
        userProfileDto.setBirthDate(usersEntity.getBirthDate());
        userProfileDto.setCreatedAt(usersEntity.getCreatedAt());

        return userProfileDto;
    }

    // 프로필 이미지 업로드 기능
    public MessageResponseDto uploadProfileImage(MultipartFile multipartFile) {
        UsersEntity userEntity = authService.getUser();

        String profileDir = String.format("media/profile/%d/", userEntity.getId());

        try {
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String[] fileNameSplit = originalFilename.split("\\.");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        String profileFilename = "image." + extension;
        String profilePath = profileDir + profileFilename;

        try {
            multipartFile.transferTo(Path.of(profilePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        userEntity.setProfileImg(profilePath);
        usersRepository.save(userEntity);

        return new MessageResponseDto("이미지가 등록되었습니다.");
    }

    // TODO 사용자 정보 수정 PUT 엔드포인트

    // TODO 사용자 삭제 DELETE 엔드포인트
}
