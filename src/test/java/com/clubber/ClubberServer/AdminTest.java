package com.clubber.ClubberServer;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdminTest {
    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService; // 테스트할 클래스

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Test
    public void testUpdateAdminPage() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Optional<Admin> admin=adminRepository.findById(currentUserId);
        Club club=admin.get().getClub(); // 해당 관리자의 동아리 가져오기


        //updateIntroduction
        System.out.println(currentUserId);
        System.out.println(club.getId());
    }


}
