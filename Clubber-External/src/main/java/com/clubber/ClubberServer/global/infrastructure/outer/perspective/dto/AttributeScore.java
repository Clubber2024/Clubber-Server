package com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class AttributeScore {
	private Score summaryScore;
	private List<SpanScore> spanScores;
}
