package com.clubber.domain.calendar.controller;

import com.clubber.domain.calendar.dto.GetCalendarInListResponse;
import com.clubber.domain.calendar.dto.GetCalendarResponse;
import com.clubber.domain.calendar.dto.GetNextAlwaysCalendarRequest;
import com.clubber.domain.calendar.dto.GetTodayCalendarResponse;
import com.clubber.domain.calendar.service.CalendarService;
import com.clubber.global.common.slice.SliceResponse;
import com.clubber.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    @Operation(summary = "특정 캘린더 (단일) 조회")
    @DisableSwaggerSecurity
    public GetCalendarResponse getCalendarResponse(@PathVariable Long id) {
        return calendarService.getCalendar(id);
    }

    @GetMapping("/today")
    @Operation(summary = "오늘 마감 캘린더 조회")
    @DisableSwaggerSecurity
    public List<GetTodayCalendarResponse> getCalendarResponse() {
        return calendarService.getTodayCalendarResponseList();
    }

    @PostMapping("/next-always")
    @Operation(summary = "다음 상시 모집 캘린더 조회")
    @DisableSwaggerSecurity
    public SliceResponse<GetCalendarResponse> getCalendarResponse(@RequestBody GetNextAlwaysCalendarRequest request) {
        return calendarService.getNextCalendar(request);
    }
}
