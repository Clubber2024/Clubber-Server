package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.CreateLinkedCalendarRequest;
import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.CreateLinkedCalenderResponse;
import com.clubber.ClubberServer.domain.recruit.service.RecruitLinkedCalendarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins/calendars/link")
@RequiredArgsConstructor
@Tag(name = "[관리자 연동 캘린더 관련 API]")
public class RecruitLinkedCalendarController {

    private final RecruitLinkedCalendarService recruitLinkedCalendarService;

    @PostMapping
    public CreateLinkedCalenderResponse createLinkedCalender(@RequestBody CreateLinkedCalendarRequest request) {
        return recruitLinkedCalendarService.createLinkedCalender(request);
    }

    @PatchMapping("/{id}/unlink")
    public void unlinkCalendar(@PathVariable Long id) {
        recruitLinkedCalendarService.unlinkCalendar(id);
    }
}
