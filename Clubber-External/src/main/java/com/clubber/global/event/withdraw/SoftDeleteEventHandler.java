package com.clubber.global.event.withdraw;

import com.clubber.domain.favorite.service.FavoriteService;
import com.clubber.domain.recruit.service.RecruitService;
import com.clubber.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SoftDeleteEventHandler {
    private final ReviewService reviewService;
    private final FavoriteService favoriteService;
    private final RecruitService recruitService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSoftDelete(SoftDeleteEvent event) {
        Long clubId = event.clubId();
        reviewService.softDeleteReviewByClubId(clubId);
        favoriteService.softDeleteByClubId(clubId);
        recruitService.softDeleteByClubId(clubId);
    }
}
