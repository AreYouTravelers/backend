package com.example.domain.boards.exception;

import com.example.global.exception.domainErrorCode.BoardErrorCode;
import com.example.global.exception.TravelersException;

public class BoardNotFoundException extends TravelersException {
    public BoardNotFoundException() {
        super(BoardErrorCode.BOARD_NOT_FOUND);
    }
}
