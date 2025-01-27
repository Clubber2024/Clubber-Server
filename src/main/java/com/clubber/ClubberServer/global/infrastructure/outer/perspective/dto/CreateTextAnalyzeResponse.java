package com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class CreateTextAnalyzeResponse {
	private Map<AttributeType, AttributeScore> attributeScores;
	private List<String> languages;

	@Override
	public String toString() {
		return "CreateTextAnalyzeResponse{" +
			"attributeScores=" + attributeScores +
			", languages=" + languages +
			'}';
	}
}
