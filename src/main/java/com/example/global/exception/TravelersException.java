package com.example.global.exception;

import lombok.Getter;

/*
[상위 예외 클래스 : 모든 커스텀 예외가 공통적으로 상속받는 기본 클래스]
0. 정의
   - 이 클래스는 애플리케이션 내에서 발생하는 모든 커스텀 예외의 기본 클래스입니다.
   - 모든 커스텀 예외 클래스는 이 클래스를 상속받도록 설계되었습니다.
1. 공통 속성
   - 모든 커스텀 예외에 공통적으로 필요한 속성이나 메서드를 정의합니다.
   - 예를 들어, 예외와 관련된 에러 코드(ErrorCode)를 저장할 수 있습니다.
2. 일관된 예외 처리
   - 글로벌 예외 처리기에서 이 상위 예외 클래스를 기반으로 일관된 예외 처리를 할 수 있습니다.
   - 이를 통해 애플리케이션 전반에서 발생하는 다양한 예외를 한 곳에서 처리할 수 있습니다.
3. 유지보수 용이성
   - 예외 처리를 중앙에서 관리하기 때문에 코드의 유지보수와 확장이 용이해집니다.
 */
@Getter
public class TravelersException extends RuntimeException {
    private final ErrorCode errorCode;

    public TravelersException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
