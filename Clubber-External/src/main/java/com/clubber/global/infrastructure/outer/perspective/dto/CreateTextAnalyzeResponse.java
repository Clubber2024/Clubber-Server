package com.clubber.global.infrastructure.outer.perspective.dto;

import java.util.List;
import java.util.Map;

public record CreateTextAnalyzeResponse(Map<AttributeType, AttributeScore> attributeScores,
										List<String> languages) {

}
