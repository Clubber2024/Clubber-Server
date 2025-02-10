package com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto;

import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CreateTextAnalyzeRequest {

	TextDto comment;
	Map<AttributeType, RequestedAttribute> requestedAttributes;
	List<String> languages;
}
