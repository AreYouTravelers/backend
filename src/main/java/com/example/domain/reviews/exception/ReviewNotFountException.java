package com.example.domain.reviews.exception;

import com.example.global.exception.domainErrorCode.ReviewErrorCode;
import com.example.global.exception.TravelersException;

public class ReviewNotFountException extends TravelersException {
    public ReviewNotFountException() {
        super(ReviewErrorCode.REVIEW_NOT_FOUND);
    }
}
