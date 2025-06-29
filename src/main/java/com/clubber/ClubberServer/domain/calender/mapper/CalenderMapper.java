package com.clubber.ClubberServer.domain.calender.mapper;

import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import org.springframework.stereotype.Component;

@Component
public class CalenderMapper {

    public Calender toCalender(Recruit recruit, String url) {
        return Calender.builder()
                .title(recruit.getTitle())
                .recruitType(recruit.getRecruitType())
                .url(url)
                .startAt(recruit.getStartAt())
                .endAt(recruit.getEndAt())
                .writerRole(AccountRole.ADMIN)
                .build();
    }
}
