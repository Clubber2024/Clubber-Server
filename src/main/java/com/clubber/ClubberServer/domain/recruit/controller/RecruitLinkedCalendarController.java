package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.CreateLinkedCalendarRequest;
import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.CreateLinkedCalenderResponse;
import com.clubber.ClubberServer.domain.recruit.service.RecruitLinkedCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendars/linked")
@RequiredArgsConstructor
public class RecruitLinkedCalendarController {

    private final RecruitLinkedCalendarService recruitLinkedCalendarService;

    @PostMapping
    public CreateLinkedCalenderResponse createLinkedCalender(@RequestBody CreateLinkedCalendarRequest request) {
        return recruitLinkedCalendarService.createLinkedCalender(request);
    }
}
