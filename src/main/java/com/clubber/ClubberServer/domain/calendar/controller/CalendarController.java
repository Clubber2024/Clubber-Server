package com.clubber.ClubberServer.domain.calendar.controller;

import com.clubber.ClubberServer.domain.calendar.dto.*;
import com.clubber.ClubberServer.domain.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calendars")
public class CalendarController {
    private final CalendarService calendarService;

    @PostMapping
    public CreateCalendarResponse createCalendar(@RequestBody CreateCalendarRequest request) {
        return calendarService.createCalendar(request);
    }

    @PostMapping("/linked")
    public CreateLinkedCalenderResponse createLinkedCalender(@RequestBody CreateLinkedCalendarRequest request) {
        return calendarService.createLinkedCalender(request);
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
