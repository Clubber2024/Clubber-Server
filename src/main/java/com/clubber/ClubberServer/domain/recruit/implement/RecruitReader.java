package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitReader {

    private final RecruitRepository recruitRepository;

    public Recruit findRecruitById(Long id) {
        return recruitRepository.queryRecruitsById(id)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
    }

    public Page<Recruit> findRecruitPagesByClub(Club club, Pageable pageable) {
        return recruitRepository.queryRecruitsByClub(club, pageable);
    }

    public List<Recruit> findTop5Recruits() {
        List<Recruit> recruits = recruitRepository.queryTop5Recruits();

        if (recruits.isEmpty()) {
            throw RecruitNotFoundException.EXCEPTION;
        }
        return recruits;
    }

    public Page<Recruit> findAllRecruits(Pageable pageable) {
        return recruitRepository.queryAllRecruits(pageable);
    }
}
