package com.clubber.ClubberServer.domain.calendar.repository;

import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
