package com.clubber.domain.calendar.repository;

import com.clubber.domain.calendar.domain.Calendar;
import com.clubber.domain.calendar.dto.GetAlwaysCalendarResponse;
import com.clubber.domain.recruit.domain.RecruitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long>, CalendarCustomRepository {


    @Query("SELECT c FROM Calendar c " +
            "WHERE (c.startAt >=:startOfMonth AND c.startAt<:startOfNextMonth " +
            "OR c.endAt >=:startOfMonth AND c.endAt <:startOfNextMonth) " +
            "AND c.recruitType IN (:recruitTypes)" +
            "AND c.isDeleted = false")
    List<Calendar> findCalendarsWithinDateRange(LocalDateTime startOfMonth,
                                                LocalDateTime startOfNextMonth, List<RecruitType> recruitTypes);


    @Query("SELECT new com.clubber.domain.calendar.dto.GetAlwaysCalendarResponse( " +
            "c.club.id, c.club.name,CAST(COUNT(c)-1 AS long)) " +
            "FROM Calendar c " +
            "WHERE c.recruitType = :recruitType " +
            "AND c.createdAt <:startOfNextMonth " +
            "AND c.isDeleted = false " +
            "GROUP BY c.club.id, c.club.name")
    List<GetAlwaysCalendarResponse> findAlwaysRecruitCreatedBefore(LocalDateTime startOfNextMonth,
                                                                   RecruitType recruitType
    );

    Optional<Calendar> findCalendarByIdAndIsDeleted(Long id, boolean deleted);
}
