package com.clubber.ClubberServer.domain.calendar.controller;

import com.clubber.ClubberServer.domain.calendar.dto.*;
import com.clubber.ClubberServer.domain.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CalendarController {
    private final CalendarService calendarService;

    @PostMapping("/calendars/")
    public CreateCalendarResponse createCalendar(@RequestBody CreateCalendarRequest request) {
        return calendarService.createCalendar(request);
    }

    @PostMapping("/calendars/linked")
    public void createLinkedCalender(@RequestBody CreateLinkedCalendarRequest request) {
        calendarService.createLinkedCalender(request);
    }

    @GetMapping("/calendars/{id}")
    public GetCalendarResponse getCalendar(@PathVariable Long id) {
        return calendarService.getCalendar(id);
    }

    @PatchMapping("/calendars/{id}")
    public void updateCalendar(@PathVariable Long id, @RequestBody UpdateCalendarRequest request) {
        calendarService.updateCalendar(request, id);
    }

    @DeleteMapping("/calendars/{id}")
    public void deleteCalendar(@PathVariable Long id) {
        calendarService.deleteCalendar(id);
    }
}
