package com.clubber.ClubberServer.domain.calendar.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.calendar.domain.CalendarStatus;
import com.clubber.ClubberServer.domain.calendar.domain.OrderStatus;
import com.clubber.ClubberServer.domain.calendar.dto.*;
import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarAppender;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarAdminService {

    private final CalendarAppender calendarAppender;
    private final CalendarReader calendarReader;
    private final AdminReader adminReader;

    public CreateCalendarResponse createCalendar(CreateCalendarRequest request) {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();
        Calendar calendar = request.toEntity(club);
        Calendar savedCalendar = calendarAppender.append(calendar);
        return CreateCalendarResponse.from(savedCalendar);
    }

    public PageResponse<GetCalendarResponseWithLinkedStatus> getCalenderPages(Pageable pageable, CalendarStatus calendarStatus, RecruitType recruitType, OrderStatus orderStatus) {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();
        return calendarReader.readClubCalendarPage(club, calendarStatus, recruitType, pageable, orderStatus);
    }

    public GetCalendarResponse getCalendar(Long id) {
        Calendar calendar = calendarReader.readById(id);
        return GetCalendarResponse.from(calendar);
    }

    public void updateCalendar(UpdateCalendarRequest request, Long recruitId) {
        Calendar calendar = calendarReader.readById(recruitId);
        calendarAppender.update(calendar, request);
    }

    public void deleteCalendar(Long calendarId) {
        Calendar calendar = calendarReader.readById(calendarId);
        calendarAppender.delete(calendar);
    }

    public GetCalendarDuplicateResponse checkDuplicateCalendar(GetCalendarDuplicateRequest request) {
        Club club = adminReader.getCurrentAdmin().getClub();
        boolean isExist = calendarReader.isExistInSameMonth(request, club);
        return new GetCalendarDuplicateResponse(isExist, request.recruitType());
    }
}
