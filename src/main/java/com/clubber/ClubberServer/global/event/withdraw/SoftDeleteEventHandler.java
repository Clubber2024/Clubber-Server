package com.clubber.ClubberServer.global.event.withdraw;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.favorite.service.FavoriteService;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import com.clubber.ClubberServer.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SoftDeleteEventHandler {
    private final AdminRepository adminRepository;
    private final ReviewService reviewService;
    private final FavoriteService favoriteService;
    private final RecruitService recruitService;

    @Async
    @TransactionalEventListener
    public void handleSoftDelete(SoftDeleteEvent event) {
        Long adminId = event.getAdminId();
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
        Long clubId = admin.getClub().getId();
        admin.deleteClub();
        reviewService.softDeleteReviewByClubId(clubId);
        favoriteService.softDeleteByClubId(clubId);
        recruitService.softDeleteByClubId(clubId);
    }
}
