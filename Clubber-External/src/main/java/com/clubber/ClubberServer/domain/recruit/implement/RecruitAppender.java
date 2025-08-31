package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.calendar.implement.CalendarAppender;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.dto.UpdateRecruitRequest;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class RecruitAppender {
    private final RecruitRepository recruitRepository;
    private final CalendarAppender calendarAppender;

    public void delete(Recruit recruit) {
        recruit.delete();
    }

    public void increaseTotalView(Recruit recruit) {
        recruit.increaseTotalview();
    }

    public Recruit append(Recruit recruit) {
        return recruitRepository.save(recruit);
    }

    public Boolean checkAndUpdateCalendarLink(Recruit recruit, UpdateRecruitRequest requestPage) {
        Boolean requestToLink = requestPage.getIsCalendarLinked();
        Boolean shouldCreateCalendar = Boolean.FALSE;

        if (recruit.isCalendarLinked() && !requestToLink) {  // 연동된 캘린더의 해제
            recruit.unlinkCalendar();
        } else if (recruit.isCalendarLinked() && requestToLink) {// 연동된 캘린더의 연동 유지
            calendarAppender.update(recruit.getCalendar(), requestPage.getTitle(), requestPage.getRecruitType(), requestPage.getStartAt(), requestPage.getEndAt());
        } else if (!recruit.isCalendarLinked() && requestToLink) { // 새로 연동
            shouldCreateCalendar = Boolean.TRUE;
        }
        return shouldCreateCalendar;
    }
}
