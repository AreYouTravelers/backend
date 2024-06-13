package com.example.domain.accompany.exception;

import com.example.global.exception.AccompanyErrorCode;
import com.example.global.exception.TravelersException;

public class AccompanyRequestFromAuthorException extends TravelersException {
    public AccompanyRequestFromAuthorException() {
        super(AccompanyErrorCode.ACCOMPANY_REQUEST_FROM_AUTHOR);
    }
}
