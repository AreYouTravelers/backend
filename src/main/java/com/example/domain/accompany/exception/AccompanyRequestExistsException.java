package com.example.domain.accompany.exception;

import com.example.global.exception.AccompanyErrorCode;
import com.example.global.exception.TravelersException;

public class AccompanyRequestExistsException extends TravelersException {
    public AccompanyRequestExistsException() {
        super(AccompanyErrorCode.ACCOMPANY_REQUEST_EXISTS);
    }
}
