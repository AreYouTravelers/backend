package com.example.domain.users.dto;

import com.example.domain.users.domain.Users;
import com.example.domain.users.domain.UsersRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/*
    UserDetails 인터페이스를 구현한 사용자 정보 클래스
    Spring Security 에서 사용자의 인증과 권한을 다루기 위해
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;                 // 사용자의 ID
    private String username;         // 사용자의 이름 (아이디)
    private String password;         // 사용자의 암호화된 비밀번호
    private String profileImg;       // 사용자의 프로필 이미지 경로
    private String email;            // 사용자의 이메일
    private Double temperature;      // 사용자의 여행온도
    private String mbti;             // 사용자의 mbti
    private String gender;           // 사용자의 성별
    private UsersRole role;          // 사용자의 역할
    private String fullName;         // 사용자의 이름
    private LocalDate birthDate;     // 사용자의 생년월일
    private LocalDateTime createdAt; // 사용자를 생성한 시간
    private LocalDateTime deletedAt; // 사용자를 삭제한 시간

    // 사용자의 권한 정보를 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 사용자의 이름(아이디)을 반환하는 메서드
    @Override
    public String getUsername() {
        return this.username;
    }

    // 사용자의 암호화된 비밀번호를 반환하는 메서드
    @Override
    public String getPassword() {
        return this.password;
    }

    // 사용자의 계정이 만료되지 않았음을 나타내는 메서드
    // true 로 변경하여 체크를 사용하지 않도록 함
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자의 계정이 잠기지 않았음을 나타내는 메서드
    // true 로 변경하여 체크를 사용하지 않도록 함
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자의 인증 정보가 만료되지 않았음을 나타내는 메서드
    // true 로 변경하여 체크를 사용하지 않도록 함
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자의 계정이 활성화되었음을 나타내는 메서드
    // true 로 변경하여 체크를 사용하지 않도록 함
    @Override
    public boolean isEnabled() {
        return true;
    }

    // CustomUserDetails 객체를 UserEntity 객체로 변환하는 메서드
    public static CustomUserDetails fromEntity(Users entity) {
        CustomUserDetails details = new CustomUserDetails();
        details.id = entity.getId();
        details.username = entity.getUsername();
        details.password = entity.getPassword();
        details.profileImg = entity.getProfileImg();
        details.email = entity.getEmail();
        details.temperature = entity.getTemperature();
        details.mbti = entity.getMbti();
        details.gender = entity.getGender();
        details.role = entity.getRole();
        details.fullName = entity.getFullName();
        details.birthDate = entity.getBirthDate();
        details.createdAt = entity.getCreatedAt();
        details.deletedAt = entity.getDeletedAt();
        return details;
    }

    // 새로운 UsersEntity 객체를 생성하고, CustomUserDetails 객체의 정보로 초기화하는 메서드
    public Users newEntity() {
        Users entity = new Users();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setProfileImg(profileImg);
        entity.setEmail(email);
        entity.setTemperature(temperature);
        entity.setMbti(mbti);
        entity.setGender(gender);
        entity.setRole(UsersRole.MEMBER);
        entity.setFullName(fullName);
        entity.setBirthDate(birthDate);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);
        return entity;
    }

    // CustomUserDetails 객체의 문자열 표현을 반환하는 메서드
    // 디버깅 or 로깅

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", email='" + email + '\'' +
                ", temperature=" + temperature +
                ", mbti='" + mbti + '\'' +
                ", gender='" + gender + '\'' +
                ", role='" + role + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}