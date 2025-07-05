package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.calendar.dto.CreateLinkedCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.service.CalendarService;
import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.GetCalendarInListResponse;
import com.clubber.ClubberServer.domain.recruit.service.RecruitCalendarService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "[캘린더 API]")
public class recruitCalendarController {

    private final RecruitCalendarService recruitCalendarService;
    private final CalendarService calendarService;

    @DisableSwaggerSecurity
    @Operation(summary = "캘린더 조회")
    @GetMapping("/calendar")
    public GetCalendarInListResponse getRecruitCalendar(@RequestParam int year,
        @RequestParam int month) {
        return recruitCalendarService.getRecruitCalendar(year, month);
    }

}
