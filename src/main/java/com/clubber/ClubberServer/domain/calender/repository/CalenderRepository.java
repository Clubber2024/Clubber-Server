package com.clubber.ClubberServer.domain.calender.repository;

import com.clubber.ClubberServer.domain.calender.entity.Calender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
}
