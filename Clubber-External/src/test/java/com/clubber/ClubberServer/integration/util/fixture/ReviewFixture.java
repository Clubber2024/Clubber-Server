package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.ReportStatus;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.user.domain.User;

public class ReviewFixture {
    public static final String CONTENT = "content";
    public static final ReportStatus REPORT_STATUS = ReportStatus.HIDDEN;

    public static Review.ReviewBuilder aReview() {
        User user = UserFixture.aUser().build();
        Club club = ClubFixture.aClub().build();
        return Review.builder()
                .club(club)
                .user(user)
                .content(CONTENT)
                .reportStatus(REPORT_STATUS)
                .isDeleted(false)
                .likes(0L);
    }
}
