package com.clubber.ClubberServer.domain.calender.controller;

import com.clubber.ClubberServer.domain.calender.dto.CreateCalenderRequest;
import com.clubber.ClubberServer.domain.calender.dto.CreateCalenderResponse;
import com.clubber.ClubberServer.domain.calender.dto.UpdateCalenderRequest;
import com.clubber.ClubberServer.domain.calender.service.CalenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CalenderController {
    private final CalenderService calenderService;

    @PostMapping("/calenders/")
    public CreateCalenderResponse createCalender(@RequestBody CreateCalenderRequest request) {
        return calenderService.createCalender(request);
    }

    @PatchMapping("/calenders/{id}")
    public void updateCalender(@PathVariable Long id, @RequestBody UpdateCalenderRequest request) {
        calenderService.updateCalender(request, id);
    }

    @DeleteMapping("/calenders/{id}")
    public void deleteCalender(@PathVariable Long id) {
        calenderService.deleteCalender(id);
    }
}
