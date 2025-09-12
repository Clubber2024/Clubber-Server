package com.clubber.ClubberServer.domain.club.repository;

import com.clubber.domain.domains.club.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubCustomRepository{

    List<Club> findByDivisionAndIsDeleted(Division division, boolean isDeleted);

    List<Club> findByDepartmentAndIsDeleted(Department department, boolean isDeleted);

    List<Club> findByHashtagAndIsDeletedOrderByClubType(Hashtag hashtag, boolean isDeleted);

    Optional<Club> findClubByIdAndIsDeleted(Long id, boolean isDeleted);

    Optional<Club> findClubByNameAndIsDeleted(String name, boolean isDeleted);

    boolean existsClubByIdAndIsDeleted(Long clubId, boolean isDeleted);

    @Query("SELECT c FROM Club c WHERE c.name LIKE %:name% AND c.isDeleted = false ORDER BY c.clubType")
    List<Club> findByNameOrderByClubType(String name);

    @Query("SELECT c FROM Club c JOIN FETCH c.clubInfo where c.isDeleted = false ORDER BY c.clubInfo.totalView DESC")
    List<Club> findTop10ByOrderByClubInfoTotalViewDesc(Pageable pageable);

    List<Club> findByClubTypeAndIsDeletedFalse(ClubType clubType);

    @Query("SELECT c FROM Club c WHERE c.name LIKE %:name% AND c.isDeleted = false ORDER BY c.name ASC")
    List<Club> findByNameOrderByName(String name);
}
