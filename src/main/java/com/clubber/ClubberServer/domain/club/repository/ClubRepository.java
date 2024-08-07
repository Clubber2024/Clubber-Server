package com.clubber.ClubberServer.domain.club.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByDivisionAndIsDeleted(String division, boolean isDeleted);

    List<Club> findByDepartmentAndIsDeleted(String department, boolean isDeleted);

    List<Club> findByHashtagAndIsDeletedOrderByClubType(String hashtag, boolean isDeleted);

    Optional<Club> findClubByIdAndIsDeleted(Long id, boolean isDeleted);

    boolean existsClubByIdAndIsDeleted(Long clubId, boolean isDeleted);

    @Query("SELECT c FROM Club c WHERE c.name LIKE %:name% AND c.isDeleted = false ORDER BY c.clubType")
    List<Club> findByName(String name);

    @Query("SELECT c FROM Club c JOIN FETCH c.clubInfo where c.isDeleted = false ORDER BY c.clubInfo.totalView DESC")
    List<Club> findTop10ByOrderByClubInfoTotalViewDesc(Pageable pageable);


}
