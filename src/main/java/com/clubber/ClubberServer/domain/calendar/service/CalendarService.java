package com.clubber.ClubberServer.domain.calendar.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.UpdateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarAppender;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {
    private final CalendarAppender calendarAppender;
    private final CalendarReader calendarReader;
    private final AdminReader adminReader;

    public CreateCalendarResponse createCalendar(CreateCalendarRequest request) {
        Calendar calendar = request.toEntity();
        Calendar savedCalendar = calendarAppender.append(calendar);
        return CreateCalendarResponse.from(savedCalendar);
    }

    public PageResponse<Calendar> getCalenderPages(Pageable pageable) {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();
        Page<Calendar> calendars = calendarReader.readClubCalendarPage(club, pageable);
        return PageResponse.of(calendars);
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
}
