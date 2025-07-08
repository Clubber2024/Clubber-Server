package com.clubber.ClubberServer.domain.calendar.controller;

import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.UpdateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.service.CalendarAdminService;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/calendars")
@Tag(name = "[관리자 캘린더 관련 API]")
public class CalendarAdminController {
    private final CalendarAdminService calendarService;

    @PostMapping
    @Operation(summary = "미연동 캘린더 생성")
    public CreateCalendarResponse createCalendar(@RequestBody CreateCalendarRequest request) {
        return calendarService.createCalendar(request);
    }

    @GetMapping
    @Operation(summary = "캘린더 목록 (페이지) 조회")
    public PageResponse<Calendar> getCalendars(Pageable pageable) {
        return calendarService.getCalenderPages(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 캘린더 (단일) 조회")
    public GetCalendarResponse getCalendar(@PathVariable Long id) {
        return calendarService.getCalendar(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "특정 캘린더 수정")
    public void updateCalendar(@PathVariable Long id, @RequestBody UpdateCalendarRequest request) {
        calendarService.updateCalendar(request, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "특정 캘린더 삭제")
    public void deleteCalendar(@PathVariable Long id) {
        calendarService.deleteCalendar(id);
    }
}
