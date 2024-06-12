package com.example.domain.accompany.exception;

import com.example.global.exception.AccompanyErrorCode;
import com.example.global.exception.TravelersException;

public class AccompanyRequestConflictException extends TravelersException {
    public AccompanyRequestConflictException() {
        super(AccompanyErrorCode.ACCOMPANY_REQUEST_CONFLICT);
    }
}
