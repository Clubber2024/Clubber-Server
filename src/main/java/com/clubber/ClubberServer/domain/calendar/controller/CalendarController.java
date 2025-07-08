package com.clubber.ClubberServer.domain.calendar.controller;

import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.UpdateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.service.AdminCalendarService;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/calendars")
public class CalendarController {
    private final AdminCalendarService calendarService;

    @PostMapping
    public CreateCalendarResponse createCalendar(@RequestBody CreateCalendarRequest request) {
        return calendarService.createCalendar(request);
    }

    @GetMapping
    public PageResponse<Calendar> getCalendars(Pageable pageable) {
        return calendarService.getCalenderPages(pageable);
    }

    @GetMapping("/{id}")
    public GetCalendarResponse getCalendar(@PathVariable Long id) {
        return calendarService.getCalendar(id);
    }

    @PatchMapping("/{id}")
    public void updateCalendar(@PathVariable Long id, @RequestBody UpdateCalendarRequest request) {
        calendarService.updateCalendar(request, id);
    }

    @DeleteMapping("/calendars/{id}")
    public void deleteCalendar(@PathVariable Long id) {
        calendarService.deleteCalendar(id);
    }
}
