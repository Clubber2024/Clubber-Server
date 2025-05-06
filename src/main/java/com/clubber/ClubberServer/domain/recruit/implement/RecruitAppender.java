package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitAppender {
    private final RecruitRepository recruitRepository;
    public void delete(Recruit recruit) {
        recruit.delete();
    }

    public void increaseTotalView(Recruit recruit) {
        recruit.increaseTotalview();
    }

    public Recruit append(Recruit recruit) {
        return recruitRepository.save(recruit);
    }
}
