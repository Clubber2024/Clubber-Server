package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.recruit.dto.*;
import com.clubber.ClubberServer.domain.recruit.dto.mainPage.GetOneRecruitMainPageResponse;
import com.clubber.ClubberServer.domain.recruit.dto.mainPage.GetRecruitsMainPageResponse;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "[모집글]")
public class recruitController {

    private final RecruitService recruitService;

    @GetMapping("/v1/clubs/{clubId}/recruit")
    @DisableSwaggerSecurity
    @Operation(summary = "특정 동아리 모집글 조회")
    public PageResponse<GetOneRecruitResponse> getRecruitsByClubId(@PathVariable("clubId")Long clubId,
                                                                        @PageableDefault(size = 5) Pageable pageable){
        return recruitService.getRecruitsByClubId(clubId,pageable);
    }

    @GetMapping("/v1/recruits/main-page")
    @DisableSwaggerSecurity
    @Operation(summary = "메인 페이지 홍보 게시판")
    public GetRecruitsMainPageResponse getRecruitsMainPage(){
        return recruitService.getRecruitsMainPage();
    }


    @GetMapping("/v1/recruits")
    @DisableSwaggerSecurity
    @Operation(summary = "홍보 게시판에서 모든 모집글 조회")
    public PageResponse<GetOneRecruitResponse> getAllRecruitsPage(@PageableDefault(size = 5) Pageable pageable){
        return recruitService.getAllRecruitsPage(pageable);
    }


    @GetMapping("/v1/recruits/{recruitId}")
    @DisableSwaggerSecurity
    @Operation(summary = "홍보 게시판에서 개별 모집글 조회")
    public GetOneRecruitResponse getRecruitsByRecruitId(@PathVariable("recruitId")Long recruitId){
        return recruitService.getRecruitsByRecruitId(recruitId);
    }

}
