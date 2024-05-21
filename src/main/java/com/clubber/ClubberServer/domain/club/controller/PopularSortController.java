package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.dto.DivisionCenterDto;
import com.clubber.ClubberServer.domain.club.dto.PopularClubDto;
import com.clubber.ClubberServer.domain.club.dto.SimpleCenterDto;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "[인기순위]")
public class PopularSortController {

    private final ClubService clubService;

    @DisableSwaggerSecurity
    @Operation(summary="인기순위 조회")
    @GetMapping("/v1/clubs/popular")
    public List<PopularClubDto> getTop10PopularClubs(){
        return clubService.getPopularClubs();
    }
}
