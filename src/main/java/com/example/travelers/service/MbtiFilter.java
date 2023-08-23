package com.example.travelers.service;

import com.example.travelers.dto.BoardDto;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.repos.BoardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MbtiFilter {
    @Autowired
    private BoardsRepository boardRepository;

    public List<BoardDto> FilteredByMBTI(String mbtiCriteria) {
        if (!isMbtiValid(mbtiCriteria)) {
            throw new IllegalArgumentException("Invalid MBTI criteria");
        }

        List<BoardsEntity> filteredBoards = boardRepository.findByUser_MbtiContaining(mbtiCriteria);
        return filteredBoards.stream().map(BoardDto::fromEntity).collect(Collectors.toList());
    }

    private boolean isMbtiValid(String mbtiSelection) {
        return !(mbtiSelection.contains("I") && mbtiSelection.contains("E")) &&
                !(mbtiSelection.contains("S") && mbtiSelection.contains("N")) &&
                !(mbtiSelection.contains("T") && mbtiSelection.contains("F")) &&
                !(mbtiSelection.contains("J") && mbtiSelection.contains("P"));
    }
}
