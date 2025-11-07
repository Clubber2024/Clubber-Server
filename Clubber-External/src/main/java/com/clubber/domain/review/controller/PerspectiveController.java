package com.clubber.domain.review.controller;

import com.clubber.domain.review.service.PerspectiveService;
import com.clubber.global.infrastructure.outer.perspective.dto.CreateTextAnalyzeResponse;
import com.clubber.global.infrastructure.outer.perspective.dto.TextDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/perspective")
public class PerspectiveController {

	private final PerspectiveService perspectiveService;

	@PostMapping
	public CreateTextAnalyzeResponse analyze(@RequestBody TextDto textDto) {
		return perspectiveService.analyzeText(textDto);
	}
}
