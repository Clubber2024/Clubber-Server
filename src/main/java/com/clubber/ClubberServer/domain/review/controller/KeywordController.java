package com.clubber.ClubberServer.domain.review.controller;

import com.clubber.ClubberServer.domain.review.service.ReviewService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.client.PerspectiveClient;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.AttributeType;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.CreateTextAnalyzeRequest;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.CreateTextAnalyzeResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.Entry;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.RequestedAttribute;
import com.clubber.ClubberServer.global.infrastructure.outer.perspective.dto.ScoreType;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/keywords")
@RequiredArgsConstructor
@Tag(name = "[키워드]")
public class KeywordController {

    private final ReviewService reviewService;

    private final PerspectiveClient perspectiveClient;

    @Operation(summary = "리뷰 키워드 전체 목록 조회")
    @DisableSwaggerSecurity
    @GetMapping
    public List<EnumMapperVO> getTotalReviews() {

        Entry entry = new Entry("씨발");
        RequestedAttribute requestedAttribute = new RequestedAttribute(ScoreType.PROBABILITY);
        Map<AttributeType, RequestedAttribute> toxicity = Map.of(AttributeType.TOXICITY, requestedAttribute);
        List<String> korea = List.of("ko");

        CreateTextAnalyzeRequest createTextAnalyzeRequest = new CreateTextAnalyzeRequest(entry, toxicity, korea);
        CreateTextAnalyzeResponse response = perspectiveClient.textAnalyze(
            "AIzaSyCBzFA5hdBLWJPxr9wBXlDX3MEy5FXBQ88", createTextAnalyzeRequest);
        System.out.println("response = " + response.toString());
        return reviewService.getTotalKeywords();
    }

}
