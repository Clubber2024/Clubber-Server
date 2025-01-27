package com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateTextAnalyzeRequest {

	TextDto comment;
	Map<AttributeType, RequestedAttribute> requestedAttributes;
	List<String> languages;

	@Builder
	public CreateTextAnalyzeRequest(TextDto comment,
		Map<AttributeType, RequestedAttribute> requestedAttributes, List<String> languages) {
		this.comment = comment;
		this.requestedAttributes = requestedAttributes;
		this.languages = languages;
	}
}
