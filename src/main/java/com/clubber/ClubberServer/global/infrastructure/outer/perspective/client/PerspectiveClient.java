package com.clubber.ClubberServer.global.infrastructure.outer.perspective.client;

import com.clubber.ClubberServer.global.config.feign.FeignConfig;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.CreateTextAnalyzeRequest;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.CreateTextAnalyzeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(
	name = "perspective-client",
	url = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze",
	configuration = FeignConfig.class)
public interface PerspectiveClient {

	@Value("${perspective.key}")

	@PostMapping(produces = "application/json")
	CreateTextAnalyzeResponse textAnalyze(@RequestParam String key,
		@RequestBody CreateTextAnalyzeRequest createTextAnalyzeRequest);
}
