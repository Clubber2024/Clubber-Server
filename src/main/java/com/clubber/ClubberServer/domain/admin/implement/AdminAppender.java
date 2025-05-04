package com.clubber.ClubberServer.domain.admin.implement;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignUpRequest;
import com.clubber.ClubberServer.domain.admin.repository.PendingAdminInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAppender {

    private final PasswordEncoder encoder;

    private final PendingAdminInfoRepository pendingAdminInfoRepository;

    public void updatePassword(Admin admin, String password) {
        String encodedPassword = encoder.encode(password);
        admin.updatePassword(password);
    }

    public PendingAdminInfo appendPendingAdminInfo(CreateAdminSignUpRequest request) {
        String encodedPassword = encoder.encode(request.getPassword());
        PendingAdminInfo pendingAdminInfo = request.toEntity(encodedPassword);
        return pendingAdminInfoRepository.save(pendingAdminInfo);
    }
}
