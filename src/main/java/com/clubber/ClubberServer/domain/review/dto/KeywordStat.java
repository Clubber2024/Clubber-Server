package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import lombok.Getter;

@Getter
public class KeywordStat {
    private Keyword keyword;

    private Long count;

}
