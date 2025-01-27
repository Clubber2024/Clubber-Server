package com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateTextAnalyzeRequest {

	Entry comment;
	Map<AttributeType, RequestedAttribute> requestedAttributes;
	List<String> languages;
}
