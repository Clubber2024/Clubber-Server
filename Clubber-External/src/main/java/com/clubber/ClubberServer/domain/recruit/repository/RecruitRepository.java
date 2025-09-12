package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecruitRepository extends JpaRepository<Recruit, Long>, RecruitCustomRepository {

    List<Recruit> findAllByClub(Club club);

    @Query("SELECT r FROM Recruit r " +
           "WHERE (r.startAt BETWEEN :startOfMonth AND :endOfMonth " +
           "OR r.endAt BETWEEN :startOfMonth AND :endOfMonth) " +
           "AND r.isDeleted = false")
    List<Recruit> findRecruitsWithinDateRange(LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    Optional<Recruit> findByCalendarAndIsDeletedFalse(Calendar calendar);
    boolean existsByCalendarAndIsDeletedFalse(Calendar calendar);
}