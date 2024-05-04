package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequest {

    private List<Keyword> keywords;
}
