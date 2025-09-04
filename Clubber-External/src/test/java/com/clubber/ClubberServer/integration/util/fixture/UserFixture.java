package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.user.domain.User;

import static com.clubber.ClubberServer.domain.user.domain.SnsType.KAKAO;

public class UserFixture {
    public static final Long USER_ID = 1L;
    public static final String EMAIL = "ssuclubber@gmail.com";
    public static final Long SNS_ID = 10000L;

    public static User.UserBuilder aUser() {
        return User.builder()
                .id(USER_ID)
                .email(EMAIL)
                .snsType(KAKAO)
                .snsId(SNS_ID);
    }
}
