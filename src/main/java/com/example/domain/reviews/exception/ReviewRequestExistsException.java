package com.example.domain.reviews.exception;

import com.example.global.exception.ErrorCode;
import com.example.global.exception.ReviewErrorCode;
import com.example.global.exception.TravelersException;

public class ReviewRequestExistsException extends TravelersException {
    public ReviewRequestExistsException() {
        super(ReviewErrorCode.REVIEW_REQUEST_EXISTS);
    }
}
