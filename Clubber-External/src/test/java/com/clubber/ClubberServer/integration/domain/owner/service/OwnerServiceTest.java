package com.clubber.ClubberServer.integration.domain.owner.service;

import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.domains.admin.domain.PendingAdminInfo;
import com.clubber.domain.admin.repository.AdminRepository;
import com.clubber.domain.admin.repository.PendingAdminInfoRepository;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.club.domain.ClubType;
import com.clubber.domain.domains.club.repository.ClubRepository;
import com.clubber.domain.owner.service.OwnerService;
import com.clubber.domain.domains.user.domain.AccountState;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
@ActiveProfiles("test")
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private PendingAdminInfoRepository pendingAdminInfoRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Test
    void 소모임_승인시_새로_저장() {
        //given
        String clubName = "clubber";
        String username = "clubber123";

        PendingAdminInfo pendingAdminInfo = AdminFixture.aPendingAdminInfo()
                .clubName(clubName)
                .clubType(ClubType.SMALL)
                .build();
        PendingAdminInfo saved = pendingAdminInfoRepository.save(pendingAdminInfo);

        //when
        ownerService.approveClubAdmin(saved.getId());

        //then
        Club club = clubRepository.findClubByNameAndIsDeleted(clubName, false).get();
        Admin admin = adminRepository.findByClubAndAccountState(club, AccountState.ACTIVE).get();

        Assertions.assertThat(club.getName()).isEqualTo(clubName);
        Assertions.assertThat(admin.getUsername()).isEqualTo(username);
    }
}
