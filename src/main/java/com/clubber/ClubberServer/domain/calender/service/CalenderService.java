package com.clubber.ClubberServer.domain.calender.service;

import com.clubber.ClubberServer.domain.calender.dto.CreateCalenderRequest;
import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.calender.implement.CalenderAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalenderService {
    private final CalenderAppender calenderAppender;

    public void createCalender(CreateCalenderRequest request) {
        Calender calender = request.toEntity();
        calenderAppender.append(calender);
    }
}
