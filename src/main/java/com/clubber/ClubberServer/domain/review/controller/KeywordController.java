package com.clubber.ClubberServer.domain.review.controller;

import com.clubber.ClubberServer.domain.review.service.ReviewService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/keywords")
@RequiredArgsConstructor
@Tag(name = "[키워드]")
public class KeywordController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 키워드 전체 목록 조회")
    @DisableSwaggerSecurity
    @GetMapping
    public List<EnumMapperVO> getTotalReviews() {
        return reviewService.getTotalKeywords();
    }

}
