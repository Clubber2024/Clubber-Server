package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitPageRequest;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitPageResponse;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class recruitController {

    private final RecruitService recruitService;

    @PostMapping("/v1/admins/recruit")
    @Operation(summary = "관리자 권한으로 모집글 작성")
    public PostRecruitPageResponse postRecruitsPage(@RequestBody @Valid PostRecruitPageRequest request){
        return recruitService.postRecruitsPage(request);
    }
}
