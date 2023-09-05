package com.example.travelers.service;

import com.example.travelers.dto.BoardDto;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.repos.BoardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


import java.util.ArrayList;
import java.util.Arrays;


@Service
public class MbtiFilter {
    @Autowired
    private BoardsRepository boardRepository;

    public List<BoardDto> FilteredByMBTI(String mbtiCriteria) {
        if (!isMbtiValid(mbtiCriteria)) {
            throw new IllegalArgumentException("Invalid MBTI criteria");
        }

        List<BoardsEntity> filteredBoards = boardRepository.findAll().stream()
                .filter(board -> isBoardMatchCriteria(board.getUser().getMbti(), mbtiCriteria))
                .collect(Collectors.toList());
        return filteredBoards.stream().map(BoardDto::fromEntity).collect(Collectors.toList());
    }
    public List<String> generateMbtiList(String mbtiCriteria) {
        if (mbtiCriteria == null || mbtiCriteria.isEmpty()) {
            return Arrays.asList("ISTJ", "ISFJ", "INFJ", "INTJ", "ISTP", "ISFP", "INFP", "INTP", "ESTP", "ESFP", "ENFP", "ENTP", "ESTJ", "ESFJ", "ENFJ", "ENTJ");
        }
        List<String> validMbtis = new ArrayList<>();
        for (String mbti : Arrays.asList("ISTJ", "ISFJ", "INFJ", "INTJ", "ISTP", "ISFP", "INFP", "INTP", "ESTP", "ESFP", "ENFP", "ENTP", "ESTJ", "ESFJ", "ENFJ", "ENTJ")) {
            if (mbtiCriteria.chars().allMatch(ch -> mbti.indexOf(ch) != -1)) {
                validMbtis.add(mbti);
            }
        }
        return validMbtis;
    }

    private boolean isMbtiValid(String mbtiSelection) {
        if (mbtiSelection == null || mbtiSelection.trim().isEmpty()) {
            return true;
        }
        return !(mbtiSelection.contains("I") && mbtiSelection.contains("E")) &&
                !(mbtiSelection.contains("S") && mbtiSelection.contains("N")) &&
                !(mbtiSelection.contains("T") && mbtiSelection.contains("F")) &&
                !(mbtiSelection.contains("J") && mbtiSelection.contains("P"));
    }

    private boolean isBoardMatchCriteria(String boardMbti, String criteria) {
        for (String type : getMatchingTypes(criteria)) {
            if (boardMbti.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getMatchingTypes(String criteria) {
        if (criteria == null || criteria.trim().isEmpty()) {
            return Arrays.asList("INTJ", "INTP", "INFJ", "INFP", "ISTJ", "ISTP", "ISFJ", "ISFP",
                    "ENTJ", "ENTP", "ENFJ", "ENFP", "ESTJ", "ESTP", "ESFJ", "ESFP");
        }

        List<String> matchingTypes = new ArrayList<>();
        String[] I_E = criteria.contains("I") ? new String[]{"I"} : (criteria.contains("E") ? new String[]{"E"} : new String[]{"I", "E"});
        String[] S_N = criteria.contains("S") ? new String[]{"S"} : (criteria.contains("N") ? new String[]{"N"} : new String[]{"S", "N"});
        String[] T_F = criteria.contains("T") ? new String[]{"T"} : (criteria.contains("F") ? new String[]{"F"} : new String[]{"T", "F"});
        String[] J_P = criteria.contains("J") ? new String[]{"J"} : (criteria.contains("P") ? new String[]{"P"} : new String[]{"J", "P"});

        for (String ie : I_E) {
            for (String sn : S_N) {
                for (String tf : T_F) {
                    for (String jp : J_P) {
                        matchingTypes.add(ie + sn + tf + jp);
                    }
                }
            }
        }

        return matchingTypes;



    }
}
