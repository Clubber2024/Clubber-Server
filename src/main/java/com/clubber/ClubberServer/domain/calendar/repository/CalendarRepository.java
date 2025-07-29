package com.clubber.ClubberServer.domain.calendar.repository;

import com.clubber.ClubberServer.domain.calendar.dto.GetAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long>, CalendarCustomRepository {


    @Query("SELECT c FROM Calendar c " +
            "WHERE (c.startAt BETWEEN :startOfMonth AND :endOfMonth " +
            "OR c.endAt BETWEEN :startOfMonth AND :endOfMonth) " +
            "AND c.recruitType IN (:recruitTypes)" +
            "AND c.isDeleted = false")
    List<Calendar> findCalendarsWithinDateRange(LocalDateTime startOfMonth,
                                                LocalDateTime endOfMonth, List<RecruitType> recruitTypes);


    @Query("SELECT new com.clubber.ClubberServer.domain.calendar.dto.GetAlwaysCalendarResponse( " +
            "c.club.id, c.club.name,COUNT(c)) " +
            "FROM Calendar c " +
            "WHERE c.recruitType = :recruitType " +
            "AND c.createdAt <= :endOfMonth " +
            "AND c.isDeleted = false " +
            "GROUP BY c.club.id, c.club.name")
    List<GetAlwaysCalendarResponse> findAlwaysRecruitCreatedBefore(LocalDateTime endOfMonth,
                                                                   RecruitType recruitType
    );

    Optional<Calendar> findCalendarByIdAndIsDeleted(Long id, boolean deleted);
}
