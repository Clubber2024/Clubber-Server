package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitReader {

    private final RecruitRepository recruitRepository;

    public Recruit findRecruitById(Long id) {
        return recruitRepository.queryRecruitsById(id)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
    }
}
