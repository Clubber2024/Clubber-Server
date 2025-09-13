package com.clubber.domain.recruit.controller;

import com.clubber.domain.recruit.dto.GetOneRecruitInListResponse;
import com.clubber.domain.recruit.dto.GetOneRecruitWithClubResponse;
import com.clubber.domain.recruit.dto.mainPage.GetRecruitsMainPageResponse;
import com.clubber.domain.recruit.service.RecruitService;
import com.clubber.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.global.common.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "[모집글 API]")
public class RecruitController {

    private final RecruitService recruitService;

    @GetMapping("/clubs/{clubId}/recruit")
    @DisableSwaggerSecurity
    @Operation(summary = "특정 동아리 모집글 조회")
    public PageResponse<GetOneRecruitInListResponse> getRecruitsByClubId(@PathVariable("clubId")Long clubId,
                                                                         @PageableDefault(size = 5) Pageable pageable){
        return recruitService.getRecruitsByClubId(clubId,pageable);
    }

    @GetMapping("/recruits/main-page")
    @DisableSwaggerSecurity
    @Operation(summary = "메인 페이지 홍보 게시판")
    public GetRecruitsMainPageResponse getRecruitsMainPage(){
        return recruitService.getRecruitsMainPage();
    }


    @GetMapping("/recruits")
    @DisableSwaggerSecurity
    @Operation(summary = "홍보 게시판에서 모든 모집글 조회")
    public PageResponse<GetOneRecruitInListResponse> getAllRecruitsPage(@PageableDefault(size = 5) Pageable pageable){
        return recruitService.getAllRecruitsPage(pageable);
    }


    @GetMapping("/recruits/{recruitId}")
    @DisableSwaggerSecurity
    @Operation(summary = "홍보 게시판에서 개별 모집글 조회")
    public GetOneRecruitWithClubResponse getRecruitsByRecruitId(@PathVariable("recruitId")Long recruitId){
        return recruitService.getRecruitsByRecruitId(recruitId);
    }

}
