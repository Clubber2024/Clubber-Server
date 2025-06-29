package com.clubber.ClubberServer.domain.calender.service;

import com.clubber.ClubberServer.domain.calender.dto.CreateLinkedCalenderRequest;
import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.calender.mapper.CalenderMapper;
import com.clubber.ClubberServer.domain.calender.repository.CalenderRepository;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitAppender;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CalenderService {

    private final CalenderMapper calenderMapper;
    private final CalenderRepository calenderRepository;
    private final RecruitReader recruitReader;
    private final RecruitAppender recruitAppender;

    @Transactional
    public void createCalender(CreateLinkedCalenderRequest request) {
        Recruit recruit = recruitReader.findRecruitById(request.recruitId());
        Calender calender = calenderMapper.toCalender(recruit, request.url());
        Calender savedCalender = calenderRepository.save(calender);
        recruitAppender.linkToCalender(recruit, savedCalender);
    }
}
