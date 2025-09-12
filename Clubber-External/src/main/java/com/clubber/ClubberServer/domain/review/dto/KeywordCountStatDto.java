package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.domain.domains.review.domain.Keyword;

public record KeywordCountStatDto(Keyword keyword, Long count) {
}
