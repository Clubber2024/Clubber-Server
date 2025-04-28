package com.clubber.ClubberServer.global.event.withdraw;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
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

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handleSoftDelete(SoftDeleteEvent event) {
        Long adminId = event.getAdminId();
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
        admin.deleteClub();
        admin.deleteClubReviews();
        admin.deleteClubFavorites();
        admin.deleteClubRecruits();
    }
}
