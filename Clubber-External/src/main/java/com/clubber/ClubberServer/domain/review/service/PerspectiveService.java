package com.clubber.ClubberServer.domain.review.service;

import com.clubber.ClubberServer.global.infrastructure.outer.perspective.client.PerspectiveClient;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.AttributeType;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.CreateTextAnalyzeRequest;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.CreateTextAnalyzeResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.TextDto;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.RequestedAttribute;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.ScoreType;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerspectiveService {

	private final PerspectiveClient perspectiveClient;
	@Value("${perspective.key}")
	private String perspectiveKey;

	public CreateTextAnalyzeResponse analyzeText(TextDto textDto) {
		//요청 필드
		RequestedAttribute requestedAttribute = new RequestedAttribute(ScoreType.PROBABILITY);
		Map<AttributeType, RequestedAttribute> toxicity = Map.of(AttributeType.TOXICITY,
			requestedAttribute);

		//언어 설정
		List<String> korean = List.of("ko");

		CreateTextAnalyzeRequest createTextAnalyzeRequest = CreateTextAnalyzeRequest.builder()
			.comment(textDto)
			.requestedAttributes(toxicity)
			.languages(korean)
			.build();
		return perspectiveClient.textAnalyze(perspectiveKey, createTextAnalyzeRequest);
	}
}
