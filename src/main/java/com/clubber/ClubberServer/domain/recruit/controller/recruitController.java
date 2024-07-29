package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.recruit.dto.GetRecruitPageResponse;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitPageRequest;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitPageResponse;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class recruitController {

    private final RecruitService recruitService;

    @PostMapping("/v1/admins/recruit")
    @Operation(summary = "관리자 권한으로 모집글 작성")
    public PostRecruitPageResponse postRecruitsPage(@RequestBody @Valid PostRecruitPageRequest request){
        return recruitService.postRecruitsPage(request);
    }

    @GetMapping("/v1/clubs/{clubId}/recruit")
    @Operation(summary = "특정 동아리 모집글 조회")
    public GetRecruitPageResponse getRecruitPageByClub(@PathVariable("clubId")Long clubId){
        return recruitService.getRecruitPageByClub(clubId);
    }
}
