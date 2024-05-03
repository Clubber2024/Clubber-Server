package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import lombok.Getter;

@Getter
public class KeywordStats {
    Keyword keyword;

    Long count;

}
