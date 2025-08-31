package com.clubber.ClubberServer.domain.calendar.controller;

import com.clubber.ClubberServer.domain.calendar.domain.CalendarStatus;
import com.clubber.ClubberServer.domain.calendar.domain.OrderStatus;
import com.clubber.ClubberServer.domain.calendar.dto.*;
import com.clubber.ClubberServer.domain.calendar.service.CalendarAdminService;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/calendars")
@Tag(name = "[관리자 캘린더 관련 API]")
public class CalendarAdminController {
    private final CalendarAdminService calendarAdminService;

    @PostMapping
    @Operation(summary = "미연동 캘린더 생성")
    public CreateCalendarResponse createCalendar(@RequestBody @Valid CreateCalendarRequest request) {
        return calendarAdminService.createCalendar(request);
    }

    @GetMapping
    @Operation(summary = "캘린더 목록 (페이지) 조회")
    public PageResponse<GetCalendarResponseWithLinkedStatus> getCalendarPages(
            Pageable pageable,
            @RequestParam(required = false) CalendarStatus calendarStatus,
            @RequestParam(required = false) RecruitType recruitType,
            @RequestParam(required = false) OrderStatus orderStatus) {
        return calendarAdminService.getCalenderPages(pageable, calendarStatus, recruitType, orderStatus);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 캘린더 (단일) 조회")
    public GetCalendarResponse getCalendar(@PathVariable Long id) {
        return calendarAdminService.getCalendar(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "특정 캘린더 수정")
    public void updateCalendar(@PathVariable Long id, @RequestBody @Valid UpdateCalendarRequest request) {
        calendarAdminService.updateCalendar(request, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "특정 캘린더 삭제")
    public void deleteCalendar(@PathVariable Long id) {
        calendarAdminService.deleteCalendar(id);
    }

    @PostMapping("/duplicate")
    @Operation(summary = "캘린더 중복 여부 확인 API")
    public GetCalendarDuplicateResponse getCalendarDuplicate(@RequestBody @Valid GetCalendarDuplicateRequest request) {
        return calendarAdminService.checkDuplicateCalendar(request);
    }
}
