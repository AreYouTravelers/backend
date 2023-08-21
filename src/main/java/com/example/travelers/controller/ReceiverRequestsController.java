package com.example.travelers.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j // 로깅
@RestController // JSON 응답, @Controller 의 역할을 하면서, 등록된 모든 메소드에 @ResponseBody 를 포함
@RequestMapping("/receiver-requests") // 요청주소와 실제주소를 매핑하는 어노테이션
@RequiredArgsConstructor
public class ReceiverRequestsController {

}
