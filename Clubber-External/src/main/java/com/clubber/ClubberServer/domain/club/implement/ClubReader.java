package com.clubber.ClubberServer.domain.club.implement;

import com.clubber.domain.domains.club.domain.*;
import com.clubber.domain.domains.club.exception.ClubNotFoundException;
import com.clubber.domain.domains.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubReader {
    private final ClubRepository clubRepository;

    public Club findById(Long id) {
        return clubRepository.findClubByIdAndIsDeleted(id, false)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
    }

    public List<Club> findByDivision(Division division) {
        List<Club> clubs = clubRepository.findByDivisionAndIsDeleted(division, false);
        if (clubs.isEmpty()) {
            throw ClubNotFoundException.EXCEPTION;
        }
        return clubs;
    }

    public List<Club> findByDepartment(Department department) {
        List<Club> clubs = clubRepository.findByDepartmentAndIsDeleted(department, false);
        if (clubs.isEmpty()) {
            throw ClubNotFoundException.EXCEPTION;
        }
        return clubs;
    }

    public List<Club> findByHashtag(Hashtag hashtag) {
        List<Club> clubs = clubRepository.findByHashtagAndIsDeletedOrderByClubType(hashtag, false);
        if (clubs.isEmpty()) {
            throw ClubNotFoundException.EXCEPTION;
        }
        return clubs;
    }

    public List<Club> findPopularTopTenClubs() {
        Pageable topTen = PageRequest.of(0, 10);
        return clubRepository.findTop10ByOrderByClubInfoTotalViewDesc(topTen);
    }

    public List<Club> findByName(String name) {
        List<Club> clubs = clubRepository.findByNameOrderByClubType(name.toUpperCase());
        if (clubs.isEmpty()) {
            throw ClubNotFoundException.EXCEPTION;
        }
        return clubs;
    }

    public List<Club> findByNameOrderByName(String name) {
        return clubRepository.findByNameOrderByName(name.toUpperCase());
    }

    public List<Club> findByClubType(ClubType clubType) {
        return clubRepository.findByClubTypeAndIsDeletedFalse(clubType);
    }
}
