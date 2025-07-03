package com.clubber.ClubberServer.domain.calender.implement;

import com.clubber.ClubberServer.domain.calender.dto.UpdateCalenderRequest;
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

    public Calender append(Calender calender) {
        return calenderRepository.save(calender);
    }

    public void update(Calender calender, UpdateCalenderRequest request) {
        calender.update(
                request.title(),
                request.recruitType(),
                request.startAt(),
                request.endAt(),
                request.url()
        );
    }

    public void delete(Calender calender) {
        calender.delete();
    }
}
