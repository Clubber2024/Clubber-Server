package com.clubber.ClubberServer.domain.club.implement;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubReader {
    private final ClubRepository clubRepository;

    public Club findById(Long id) {
        return clubRepository.findClubByIdAndIsDeleted(id, false)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
    }
}
