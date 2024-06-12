package com.example.global.exception;

import com.example.global.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Status: {}, Class: {}, Code: {}, Message: {}";

    @ExceptionHandler(TravelersException.class)
    public ResponseEntity<ApiErrorResponse> handleTravelersException(HttpServletRequest request, TravelersException e){
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.getStatus();
        String exception = e.getClass().getSimpleName();
        String code = errorCode.getCode();
        String message = errorCode.getMessage();

        log.error(LOG_FORMAT, status, exception, code, message);

        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.of(
                        status,
                        request.getServletPath(),
                        exception,
                        code,
                        errorCode
                ));
    }
}