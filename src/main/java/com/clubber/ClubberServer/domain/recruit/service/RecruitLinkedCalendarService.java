package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarAppender;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.recruit.dto.CreateLinkedCalendarRequest;
import com.clubber.ClubberServer.domain.recruit.dto.CreateLinkedCalenderResponse;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class RecruitLinkedCalendarService {


    private final CalendarAppender calendarAppender;
    private final CalendarReader calendarReader;
    private final RecruitReader recruitReader;
    private final AdminReader adminReader;

    @Transactional
    public CreateLinkedCalenderResponse createLinkedCalender(CreateLinkedCalendarRequest request) {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();

        Recruit recruit = recruitReader.findRecruitById(request.recruitId());
        CreateCalendarRequest calendarRequest = CreateCalendarRequest.from(recruit,
            request.recruitUrl());
        Calendar savedCalendar = calendarAppender.append(calendarRequest.toEntity(club));
        recruit.linkCalendar(savedCalendar);
        return new CreateLinkedCalenderResponse(request.recruitId(), savedCalendar.getId());
    }


    @Transactional
    public void syncCalendarWithRecruit(Long recruitId, String title, RecruitType recruitType,
        LocalDateTime startAt, LocalDateTime endAt) {
        Calendar calendar = calendarReader.readById(recruitId);
        calendarAppender.update(calendar, title, recruitType, startAt, endAt);
    }


    @Transactional
    public void unlinkCalendar(Long calendarId) {
        Calendar calendar = calendarReader.readById(calendarId);
        Recruit recruit = recruitReader.findByCalendar(calendar);
        recruit.unlinkCalendar();
    }


}
