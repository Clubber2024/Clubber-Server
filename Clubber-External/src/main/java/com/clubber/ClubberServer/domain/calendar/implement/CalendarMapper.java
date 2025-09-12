package com.clubber.ClubberServer.domain.calendar.implement;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.dto.*;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import com.clubber.ClubberServer.global.common.slice.SliceResponse;
import com.clubber.ClubberServer.global.util.SliceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CalendarMapper {

    private final RecruitReader recruitReader;

    public Calendar toCalendar(Recruit recruit, Club club, String recruitUrl) {
        return Calendar.builder()
                .title(recruit.getTitle())
                .recruitType(recruit.getRecruitType())
                .startAt(recruit.getStartAt())
                .endAt(recruit.getEndAt())
                .url(recruitUrl)
                .club(club)
                .writerRole(AccountRole.ADMIN)
                .build();
    }

    public PageResponse<GetCalendarResponseWithLinkedStatus> toCalendarPageResponse(Page<Calendar> calendarPages) {
        Page<GetCalendarResponseWithLinkedStatus> pageDtos = calendarPages.map(
                calendar -> {
                    boolean isCalendarLinked = recruitReader.isCalendarLinked(calendar);
                    return GetCalendarResponseWithLinkedStatus.from(calendar, isCalendarLinked);
                }
        );
        return PageResponse.of(pageDtos);
    }

    public GetCalendarInListResponse toCalendarInListResponse(List<Calendar> nonAlwaysCalendar, List<GetAlwaysCalendarResponse> alwaysCalendarResponses, YearMonth recruitYearMonth) {
        List<GetNonAlwaysCalendarResponse> nonAlwaysCalendarResponses = nonAlwaysCalendar.stream()
                .map(GetNonAlwaysCalendarResponse::from)
                .toList();
        return GetCalendarInListResponse.of(recruitYearMonth.getYear(), recruitYearMonth.getMonthValue(), nonAlwaysCalendarResponses, alwaysCalendarResponses);
    }

    public List<GetTodayCalendarResponse> toTodayCalendarResponseList(List<Club> todayEndCalendarClubs) {
        return todayEndCalendarClubs
                .stream()
                .map(GetTodayCalendarResponse::from)
                .toList();
    }

    public SliceResponse<GetCalendarResponse> toAlwaysNextCalendarSliceResponse(List<Calendar> alwaysNextCalendars) {
        List<GetCalendarResponse> alwaysNextCalendarResponse = alwaysNextCalendars.stream()
                .map(GetCalendarResponse::from)
                .toList();
        return SliceUtil.valueOf(alwaysNextCalendarResponse, Pageable.ofSize(1));
    }
}
