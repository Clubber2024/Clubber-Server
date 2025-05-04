package com.clubber.ClubberServer.domain.admin.implement;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAppender {

    private final PasswordEncoder encoder;

    public void updatePassword(Admin admin, String password) {
        String encodedPassword = encoder.encode(password);
        admin.updatePassword(password);
    }
}
