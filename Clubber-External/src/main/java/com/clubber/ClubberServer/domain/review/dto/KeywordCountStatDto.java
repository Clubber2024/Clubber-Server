package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;

public record KeywordCountStatDto(Keyword keyword, Long count) {
}
