package com.clubber.ClubberServer.domain.calender.implement;

import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.calender.exception.CalenderNotFoundException;
import com.clubber.ClubberServer.domain.calender.repository.CalenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalenderReader {
    private final CalenderRepository calenderRepository;

    public Calender readById(Long id) {
        return calenderRepository.findById(id)
                .orElseThrow(() -> CalenderNotFoundException.EXCEPTION);
    }
}
