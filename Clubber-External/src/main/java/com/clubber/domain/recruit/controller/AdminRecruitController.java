package com.clubber.domain.recruit.controller;

import com.clubber.domain.recruit.dto.*;
import com.clubber.domain.recruit.service.RecruitService;
import com.clubber.global.common.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "[관리자 모집글 관련 API]", description = "🔐동아리 계정")
public class AdminRecruitController {

    private final RecruitService recruitService;

    @GetMapping("/admins/recruits")
    @Operation(summary = "동아리 계정의 모든 모집글 조회")
    public PageResponse<GetOneRecruitInListResponse> getAllAdminRecruits(@PageableDefault(size = 5) Pageable pageable){
        return recruitService.getAllAdminRecruits(pageable);
    }


    @PostMapping("/admins/recruits")
    @Operation(summary = "동아리 계정 모집글 작성")
    public PostRecruitResponse createRecruit(@RequestBody @Valid PostRecruitRequest request){
        return recruitService.createRecruit(request);
    }

    @DeleteMapping("/admins/recruits/{recruitId}")
    @Operation(summary = "동아리 계정 모집글 삭제")
    public DeleteRecruitByIdResponse deleteRecruitsById(@PathVariable("recruitId")Long recruitId){
        return recruitService.deleteRecruitsById(recruitId);
    }

    @GetMapping("/admins/recruits/{recruitId}")
    @Operation(summary= " 동아리 계정 개별 모집글 조회")
    public GetOneAdminRecruitResponse getOneAdminRecruitsById(@PathVariable("recruitId")Long recruitId){
        return recruitService.getOneAdminRecruitsById(recruitId);
    }

    @PatchMapping("/admins/recruits/{recruitId}")
    @Operation(summary= " 동아리 계정 개별 모집글 수정")
    public UpdateRecruitResponse changeAdminRecruits(@PathVariable("recruitId")Long recruitId,@RequestBody @Valid UpdateRecruitRequest pageRequest){
        return recruitService.changeAdminRecruits(recruitId,pageRequest);
    }


}
