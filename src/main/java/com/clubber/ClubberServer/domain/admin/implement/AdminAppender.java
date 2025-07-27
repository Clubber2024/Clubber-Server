package com.clubber.ClubberServer.domain.admin.implement;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.Contact;
import com.clubber.ClubberServer.domain.club.domain.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAppender {

    private final PasswordEncoder encoder;

    public void updatePassword(Admin admin, String password) {
        String encodedPassword = encoder.encode(password);
        admin.updatePassword(encodedPassword);
    }

    public void updateEmail(Admin admin, String email) {
        admin.updateEmail(email);
    }

    public void updateContact(Admin admin, Contact contact) {
        admin.updateContact(contact);
    }

    public void updateBySignUpApproved(Admin admin, String username, String password, Contact contact, String email) {
        admin.updateUsername(username);
        admin.updatePassword(password);
        admin.updateContact(contact);
        admin.updateEmail(email);
    }

    public Long withDraw(Admin admin) {
        admin.withDraw();
        Club club = admin.getClub();
        club.delete();
        return club.getId();
    }
}
