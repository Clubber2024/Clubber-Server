package com.clubber.ClubberServer.domain.calendar.implement;

import com.clubber.ClubberServer.domain.calendar.dto.UpdateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.repository.CalendarRepository;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class CalendarAppender {

    private final CalendarRepository calendarRepository;

    public Calendar append(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    public void update(Calendar calendar, UpdateCalendarRequest request) {
        calendar.update(
            request.title(),
            request.recruitType(),
            request.startAt(),
            request.endAt(),
            request.url()
        );
    }

    public void update(Calendar calendar, String title, RecruitType recruitType,
        LocalDateTime startAt, LocalDateTime endAt) {
        calendar.update(
            title, recruitType, startAt, endAt
        );
    }

    public void delete(Calendar calendar) {
        calendar.delete();
    }
}
