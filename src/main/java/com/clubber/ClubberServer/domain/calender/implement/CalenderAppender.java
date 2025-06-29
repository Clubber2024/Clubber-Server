package com.clubber.ClubberServer.domain.calender.implement;

import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.calender.repository.CalenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class CalenderAppender {

    private final CalenderRepository calenderRepository;

    public void append(Calender calender) {
        calenderRepository.save(calender);
    }
}
