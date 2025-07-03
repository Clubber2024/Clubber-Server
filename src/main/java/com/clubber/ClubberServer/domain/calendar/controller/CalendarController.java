package com.clubber.ClubberServer.domain.calendar.controller;

import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.UpdateCalendarRequest;
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
