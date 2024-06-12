package com.example.domain.accompany.exception;

import com.example.global.exception.AccompanyErrorCode;
import com.example.global.exception.TravelersException;

public class AccompanyNotFoundException extends TravelersException {
    public AccompanyNotFoundException() {
        super(AccompanyErrorCode.ACCOMPANY_NOT_FOUND);
    }
}
