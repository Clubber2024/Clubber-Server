package com.clubber.domain.calendar.service;

import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.calendar.domain.Calendar;
import com.clubber.domain.calendar.domain.CalendarStatus;
import com.clubber.domain.calendar.domain.OrderStatus;
import com.clubber.domain.calendar.dto.*;
import com.clubber.domain.calendar.implement.CalendarAppender;
import com.clubber.domain.calendar.implement.CalendarMapper;
import com.clubber.domain.calendar.implement.CalendarReader;
import com.clubber.domain.calendar.implement.CalendarValidator;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.recruit.domain.RecruitType;
import com.clubber.global.common.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarAdminService {

    private final CalendarMapper calendarMapper;
    private final CalendarAppender calendarAppender;
    private final CalendarReader calendarReader;
    private final CalendarValidator calendarValidator;
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
        Page<Calendar> calendars = calendarReader.readClubCalendarPage(club, calendarStatus, recruitType, pageable, orderStatus);
        return calendarMapper.toCalendarPageResponse(calendars);
    }

    public GetCalendarResponse getCalendar(Long calendarId) {
        Calendar calendar = calendarReader.readById(calendarId);
        return GetCalendarResponse.from(calendar);
    }

    public void updateCalendar(UpdateCalendarRequest request, Long calendarId) {
        Admin admin = adminReader.getCurrentAdmin();
        Calendar calendar = calendarReader.readById(calendarId);

        calendarValidator.validateCalendarClub(calendar, admin);
        calendarAppender.update(calendar, request);
    }

    public void deleteCalendar(Long calendarId) {
        Admin admin = adminReader.getCurrentAdmin();
        Calendar calendar = calendarReader.readById(calendarId);

        calendarValidator.validateCalendarClub(calendar, admin);
        calendarAppender.delete(calendar);
    }

    public GetCalendarDuplicateResponse checkDuplicateCalendar(GetCalendarDuplicateRequest request) {
        Club club = adminReader.getCurrentAdmin().getClub();
        boolean isExist = calendarReader.isExistInSameMonth(request, club);
        return new GetCalendarDuplicateResponse(isExist, request.recruitType());
    }
}
