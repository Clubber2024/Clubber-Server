package com.clubber.ClubberServer.domain.admin.implement;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.Contact;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignUpRequest;
import com.clubber.ClubberServer.domain.admin.repository.PendingAdminInfoRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
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

    public void updateEmail(Admin admin, String email){
        admin.updateEmail(email);
    }

    public void updateContact(Admin admin, Contact contact) {
        admin.updateContact(contact);
    }

    public PendingAdminInfo appendPendingAdminInfo(CreateAdminSignUpRequest request) {
        String encodedPassword = encoder.encode(request.getPassword());
        PendingAdminInfo pendingAdminInfo = request.toEntity(encodedPassword);
        return pendingAdminInfoRepository.save(pendingAdminInfo);
    }

    public Long withDraw(Admin admin) {
        admin.withDraw();
        Club club = admin.getClub();
        club.delete();
        return club.getId();
    }
}
