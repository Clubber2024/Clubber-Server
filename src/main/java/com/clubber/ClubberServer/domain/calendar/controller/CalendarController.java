package com.clubber.ClubberServer.domain.calendar.controller;

import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarInListResponse;
import com.clubber.ClubberServer.domain.calendar.service.CalendarService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calendars")
@Tag(name = "[캘린더 - 모집 일정 API]")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    @DisableSwaggerSecurity
    public GetCalendarInListResponse getCalendarList(@RequestParam int year,
                                                     @RequestParam int month) {
        return calendarService.getCalendarList(year, month);
    }
}
