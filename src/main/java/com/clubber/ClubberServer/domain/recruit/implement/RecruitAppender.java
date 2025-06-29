package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class RecruitAppender {
    private final RecruitRepository recruitRepository;
    public void delete(Recruit recruit) {
        recruit.delete();
    }

    public void increaseTotalView(Recruit recruit) {
        recruit.increaseTotalview();
    }

    public void linkToCalender(Recruit recruit, Calender calender) {
        recruit.link(calender);
    }

    public Recruit append(Recruit recruit) {
        return recruitRepository.save(recruit);
    }
}
