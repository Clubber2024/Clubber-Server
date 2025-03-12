package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import com.clubber.ClubberServer.domain.club.dto.GetClubsByHashTagResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubsSearchForSignUpResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubsSearchResponse;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clubs")
@Tag(name = "[검색]")
public class SearchController {

    private final ClubService clubService;


    // hashtag 기반 검색
    @DisableSwaggerSecurity
    @Operation(summary = "hashtag기반 검색")
    @GetMapping(params = "hashtag")
    public GetClubsByHashTagResponse searchByHashtag(
        @RequestParam(name = "hashtag", required = false) Hashtag hashtag) {
        return clubService.getClubsHashtag(hashtag);

    }

    // 동아리명 or 소모임명 기반 검색
    @DisableSwaggerSecurity
    @Operation(summary = "동아리명 및 소모임명 기반 검색")
    @GetMapping(params = "clubName")
    public GetClubsSearchResponse searchByClubName(
        @RequestParam(name = "clubName", required = false) String clubName) {
        return clubService.getClubsByName(clubName);
    }

    // 회원가입시 동아리명 검색
    @DisableSwaggerSecurity
    @Operation(summary = "회원가입시 동아리명 검색")
    @GetMapping(value = "/sign-up", params = "clubName")
    public List<GetClubsSearchForSignUpResponse> searchForSignUpByClubName(
        @RequestParam(required = false) String clubName) {
        return clubService.searchForSignUp(clubName);
    }

}