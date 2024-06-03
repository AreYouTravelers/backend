package com.example.domain.accompany.dto.request;

import com.example.domain.accompany.domain.AccompanyRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccompanyStatusRequestDto {
    private String status;      // Receiver가 동행요청을 응답할 때, 그 상태를 서버에게 넘겨줌
}
