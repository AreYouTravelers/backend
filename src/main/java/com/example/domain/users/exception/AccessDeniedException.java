package com.example.domain.users.exception;

import com.example.global.exception.TravelersException;
import com.example.global.exception.domainErrorCode.UserErrorCode;

public class AccessDeniedException extends TravelersException {
    public AccessDeniedException() {
        super(UserErrorCode.ACCESS_DENIED);
    }
}
