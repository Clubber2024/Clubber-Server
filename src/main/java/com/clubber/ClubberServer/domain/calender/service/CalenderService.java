package com.clubber.ClubberServer.domain.calender.service;

import com.clubber.ClubberServer.domain.calender.dto.CreateCalenderRequest;
import com.clubber.ClubberServer.domain.calender.dto.CreateCalenderResponse;
import com.clubber.ClubberServer.domain.calender.dto.CreateLinkedCalenderRequest;
import com.clubber.ClubberServer.domain.calender.dto.UpdateCalenderRequest;
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

    public CreateCalenderResponse createCalender(CreateCalenderRequest request) {
        Calender calender = request.toEntity();
        Calender savedCalender = calenderAppender.append(calender);
        return CreateCalenderResponse.from(savedCalender);
    }

    public void updateCalender(UpdateCalenderRequest request, Long recruitId) {
        Calender calender = calenderReader.readById(recruitId);
        calenderAppender.update(calender, request);
    }

    public void createLinkedCalender(CreateLinkedCalenderRequest linkedCalenderRequest) {
        Calender calender = calenderReader.readById(linkedCalenderRequest.recruitId());
        CreateCalenderRequest request = CreateCalenderRequest.from(calender, linkedCalenderRequest.recruitUrl());
        calenderAppender.append(request.toEntity());
    }

    public void deleteCalender(Long calenderId) {
        Calender calender = calenderReader.readById(calenderId);
        calenderAppender.delete(calender);
    }
}
