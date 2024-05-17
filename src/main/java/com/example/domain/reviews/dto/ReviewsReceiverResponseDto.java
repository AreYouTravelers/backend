package com.example.domain.reviews.dto;

import com.example.domain.boards.dto.BoardDto;
import com.example.domain.users.dto.UserProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsReceiverResponseDto {
    private UserProfileDto receiver;
    private List<BoardDto> boardList;
    private List<ReviewsDto> reviewList;
    private List<UserProfileDto> writerList;
}
