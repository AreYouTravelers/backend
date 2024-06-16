package com.example.domain.comments.exception;

import com.example.global.exception.TravelersException;
import com.example.global.exception.domainErrorCode.CommentErrorCode;

public class CommentNotFoundException extends TravelersException {
    public CommentNotFoundException() {
        super(CommentErrorCode.COMMENT_NOT_FOUND);
    }
}
