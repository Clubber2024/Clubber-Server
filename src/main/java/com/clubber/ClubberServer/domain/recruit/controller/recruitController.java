package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.recruit.dto.*;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "[모집글]")
public class recruitController {

    private final RecruitService recruitService;

    @PostMapping("/v1/admins/recruit")
    @Operation(summary = "관리자 권한으로 모집글 작성")
    public PostRecruitResponse postRecruitsPage(@RequestBody @Valid PostRecruitRequest request){
        return recruitService.postRecruitsPage(request);
    }

    @GetMapping("/v1/clubs/{clubId}/recruit")
    @Operation(summary = "특정 동아리 모집글 조회")
    public GetRecruitsByClubIdResponse getRecruitsByClubId(@PathVariable("clubId")Long clubId){
        return recruitService.getRecruitsByClubId(clubId);
    }

    @GetMapping("/v1/recruits")
    @Operation(summary = "홍보 게시판에서 모든 모집글 조회")
    public GetAllRecruitsResponse getAllRecruitsPage(){
        return recruitService.getAllRecruitsPage();
    }

    @GetMapping("/v1/recruits/{recruitId}")
    @Operation(summary = "홍보 게시판에서 개별 모집글 조회")
    public GetOneRecruitResponse getRecruitsByRecruitId(@PathVariable("recruitId")Long recruitId){
        return recruitService.getRecruitsByRecruitId(recruitId);
    }

}
