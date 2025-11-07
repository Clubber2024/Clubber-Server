package com.clubber.domain.recruit.implement;

import com.clubber.domain.calendar.domain.Calendar;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.recruit.domain.Recruit;
import com.clubber.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.domain.recruit.repository.RecruitRepository;
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

    public Recruit findByCalendar(Calendar calendar) {
        return recruitRepository.findByCalendarAndIsDeletedFalse(calendar)
                .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);
    }

    public boolean isCalendarLinked(Calendar calendar) {
        return recruitRepository.existsByCalendarAndIsDeletedFalse(calendar);
    }
}
