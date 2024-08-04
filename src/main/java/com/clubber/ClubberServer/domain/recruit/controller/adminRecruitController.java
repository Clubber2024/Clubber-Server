package com.clubber.ClubberServer.domain.recruit.controller;


import com.clubber.ClubberServer.domain.recruit.dto.DeleteRecruitByIdResponse;
import com.clubber.ClubberServer.domain.recruit.dto.GetAllRecruitsResponse;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitRequest;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitResponse;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "[동아리 계정 모집글 관련 API]")
public class adminRecruitController {

    private final RecruitService recruitService;

    @GetMapping("/v1/admins/recruits")
    @Operation(summary = "동아리 계정 작성한 모든 모집글 조회")
    public GetAllRecruitsResponse getAllAdminRecruits(){
        return recruitService.getAllAdminRecruits();
    }


    @PostMapping("/v1/admins/recruits")
    @Operation(summary = "동아리 계정 모집글 작성")
    public PostRecruitResponse postRecruitsPage(@RequestBody @Valid PostRecruitRequest request){
        return recruitService.postRecruitsPage(request);
    }

    //관리자 권한으로 모집글 삭제
    @DeleteMapping("/v1/admins/recruits/{recruitId}")
    @Operation(summary = "동아리 계정 모집글 삭제")
    public DeleteRecruitByIdResponse deleteRecruitsById(@PathVariable("recruitId")Long recruitId){
        return recruitService.deleteRecruitsById(recruitId);
    }

}
