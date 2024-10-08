package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.dto.GetClubPopularResponse;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "[조회수 기반 인기순위]")
public class PopularController {

    private final ClubService clubService;

    @DisableSwaggerSecurity
    @Operation(summary="인기순위 조회")
    @GetMapping("/api/v1/clubs/popular")
    public List<GetClubPopularResponse> getClubsPopular(){
        return clubService.getClubsPopular();
    }
}
