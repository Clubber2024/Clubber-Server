package com.clubber.ClubberServer.domain.calendar.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.CreateLinkedCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.dto.CreateLinkedCalenderResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarInListResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetNonAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.UpdateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarAppender;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarValidator;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {

    private final CalendarAppender calendarAppender;
    private final CalendarReader calendarReader;
    //TODO 패키지 순환참조 해결
    private final RecruitReader recruitReader;
    private final AdminReader adminReader;
    private final CalendarValidator calendarValidator;

    public CreateCalendarResponse createCalendar(CreateCalendarRequest request) {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();

        Calendar calendar = request.toEntity(club);
        Calendar savedCalendar = calendarAppender.append(calendar);
        return CreateCalendarResponse.from(savedCalendar);
    }

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

    public GetCalendarResponse getCalendar(Long id) {
        Calendar calendar = calendarReader.readById(id);
        return GetCalendarResponse.from(calendar);
    }

    public void updateCalendar(UpdateCalendarRequest request, Long recruitId) {
        Calendar calendar = calendarReader.readById(recruitId);
        calendarAppender.update(calendar, request);
    }

    public void syncCalendarWithRecruit(Long recruitId, String title, RecruitType recruitType,
        LocalDateTime startAt, LocalDateTime endAt) {
        Calendar calendar = calendarReader.readById(recruitId);
        calendarAppender.update(calendar, title, recruitType, startAt, endAt);
    }

    public void deleteCalendar(Long calendarId) {
        Calendar calendar = calendarReader.readById(calendarId);
        calendarAppender.delete(calendar);
    }

    @Transactional(readOnly = true)
    public GetCalendarInListResponse getCalendarList(int year, int month) {

        calendarValidator.validateCalendarMonth(month);

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);


        List<RecruitType> recruitTypes = List.of(RecruitType.REGULAR, RecruitType.ADDITIONAL);
        List<Calendar> nonAlwaysCalendars = calendarReader.findCalendarsByDateRangeAndTypes(
            startOfMonth, endOfMonth,
            recruitTypes);
        List<GetNonAlwaysCalendarResponse> nonAlwaysCalendarDto = nonAlwaysCalendars.stream()
            .map(GetNonAlwaysCalendarResponse::from)
            .toList();

        List<GetAlwaysCalendarResponse> alwaysCalendarDto = calendarReader.findCalendarsByEndDateAndType(
            endOfMonth, RecruitType.ALWAYS);

        return GetCalendarInListResponse.of(year, month, nonAlwaysCalendarDto, alwaysCalendarDto);

    }

}
