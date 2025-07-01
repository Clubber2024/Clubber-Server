package com.clubber.ClubberServer.domain.calender.service;

import com.clubber.ClubberServer.domain.calender.dto.CreateCalenderRequest;
import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.calender.implement.CalenderAppender;
import com.clubber.ClubberServer.domain.calender.implement.CalenderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalenderService {
    private final CalenderAppender calenderAppender;
    private final CalenderReader calenderReader;

    public void createCalender(CreateCalenderRequest request) {
        Calender calender = request.toEntity();
        calenderAppender.append(calender);
    }

    public void deleteCalender(Long calenderId) {
        Calender calender = calenderReader.readById(calenderId);
        calenderAppender.delete(calender);
    }
}
