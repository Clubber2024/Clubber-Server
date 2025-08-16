package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarAppender;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarMapper;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.dto.CreateLinkedCalendarRequest;
import com.clubber.ClubberServer.domain.recruit.dto.CreateLinkedCalendarResponse;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitLinkedCalendarService {

    private final RecruitValidator recruitValidator;
    private final CalendarAppender calendarAppender;
    private final CalendarReader calendarReader;
    private final CalendarMapper calendarMapper;
    private final RecruitReader recruitReader;
    private final AdminReader adminReader;

    @Transactional
    public CreateLinkedCalendarResponse createLinkedCalendar(CreateLinkedCalendarRequest request) {
        Admin admin = adminReader.getCurrentAdmin();
        Recruit recruit = recruitReader.findRecruitById(request.recruitId());
        recruitValidator.validateRecruitClub(recruit, admin);

        Calendar calendar = calendarMapper.toCalendar(recruit, admin.getClub(), request.recruitUrl());
        Calendar savedCalendar = calendarAppender.append(calendar);

        recruit.linkCalendar(savedCalendar);
        return new CreateLinkedCalendarResponse(request.recruitId(), savedCalendar.getId());
    }

    @Transactional
    public void unlinkCalendar(Long calendarId) {
        Calendar calendar = calendarReader.readById(calendarId);
        Recruit recruit = recruitReader.findByCalendar(calendar);
        recruit.unlinkCalendar();
    }
}
