package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitAppender {
    public void delete(Recruit recruit) {
        recruit.delete();
    }
}
