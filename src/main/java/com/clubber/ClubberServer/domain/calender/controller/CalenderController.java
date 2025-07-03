package com.clubber.ClubberServer.domain.calender.controller;

import com.clubber.ClubberServer.domain.calender.dto.CreateCalenderRequest;
import com.clubber.ClubberServer.domain.calender.dto.CreateCalenderResponse;
import com.clubber.ClubberServer.domain.calender.service.CalenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CalenderController {
    private final CalenderService calenderService;

    @PostMapping("/calenders/")
    public CreateCalenderResponse createCalender(@RequestBody CreateCalenderRequest request) {
        return calenderService.createCalender(request);
    }
}
