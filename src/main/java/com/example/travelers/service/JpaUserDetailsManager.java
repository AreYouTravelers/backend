package com.example.travelers.service;

import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
public class JpaUserDetailsManager implements UserDetailsManager {

    private final UsersRepository userRepository;

    public JpaUserDetailsManager (UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = usersRepository;
        // 회원 Test
        createUser(CustomUserDetails.builder()
                .username("dohun")
                .password(passwordEncoder.encode("1234"))
                .profileImg("image.png")
                .email("dohun@gmail.com")
                .temperature(36.5)
                .mbti("ESTJ")
                .gender("남")
                .role("회원")
                .firstName("김")
                .lastName("도훈")
                .birthDate(LocalDate.of(1999, 9, 6))
                .createdAt(LocalDateTime.now())
                .build()
        );
        // 관리자 Test
        createUser(CustomUserDetails.builder()
                .username("admin")
                .password(passwordEncoder.encode("123"))
                .temperature(36.5)
                .role("관리자")
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    // 사용자 이름을 통해 사용자 정보를 불러옴
    // 사용자 이름에 해당하는 사용자를 찾을 수 없는 경우 예외 발생
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        return CustomUserDetails.fromEntity(optionalUser.get());
    }

    // 새로운 사용자를 저장하는 메서드
    // 이미 존재하는 사용자명일 경우 BAD_REQUEST 예외 발생
    // 사용자 정보를 CustomUserDetails 로 캐스팅할 수 없는 경우
    // INTERNAL_SERVER_ERROR 예외 발생
    @Override
    public void createUser(UserDetails user) {
        // 사용자 생성 시도
        log.info("try create user: {}", user.getUsername());
        // 사용자 Username 의 중복여부 확인
        if (this.userExists(user.getUsername())) {
            log.info("Username: {} already exists", user.getUsername());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        // 중복이 아닐 때 로직수행 user 저장 시도
        try {
            userRepository.save(((CustomUserDetails) user).newEntity());
        } catch (ClassCastException e) {
            log.error("failed to cast to {}", CustomUserDetails.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to cast user to CustomUserDetails");
        }
    }

    // 사용자 username 을 통해 해당 사용자가 존재하는지 확인하는 메서드
    // 사용자가 존재하면 true, 존재하지 않으면 false 반환
    @Override
    public boolean userExists(String username) {
        log.info("check if user: {} exists", username);
        return this.userRepository.existsByUsername(username);
    }


    // TODO -- 미구현 --

    @Override
    public void updateUser(UserDetails user) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public void deleteUser(String username) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
