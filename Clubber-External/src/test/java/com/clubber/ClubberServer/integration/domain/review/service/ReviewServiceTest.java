package com.clubber.ClubberServer.integration.domain.review.service;

import com.clubber.ClubberServer.integration.util.fixture.ClubFixture;
import com.clubber.ClubberServer.integration.util.fixture.ReviewFixture;
import com.clubber.ClubberServer.integration.util.fixture.UserFixture;
import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.club.repository.ClubRepository;
import com.clubber.domain.domains.review.domain.Keyword;
import com.clubber.domain.domains.review.domain.ReportStatus;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.repository.ReviewRepository;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.review.dto.CreateClubReviewRequest;
import com.clubber.domain.review.dto.CreateClubReviewResponse;
import com.clubber.domain.review.service.ReviewService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.domain.user.repository.UserRepository;
import com.clubber.global.config.security.AuthDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserRepository userRepository;

    private void createSecurityContext(User user) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AuthDetails userDetails = new AuthDetails(user.getId().toString(), "USER");
        UsernamePasswordAuthenticationToken adminToken = new UsernamePasswordAuthenticationToken(
                userDetails, "user", userDetails.getAuthorities());
        context.setAuthentication(adminToken);
        SecurityContextHolder.setContext(context);
    }

    @DisplayName("리뷰 작성에 성공한다")
    @Test
    void createReviewSuccess() {
        User user = UserFixture.aUser().build();
        createSecurityContext(userRepository.save(user));

        Club club = ClubFixture.aClub().isAgreeToReview(true).build();
        clubRepository.save(club);

        String content = "content";
        List<Keyword> keywords = List.of(Keyword.ACTIVE);
        CreateClubReviewRequest request = new CreateClubReviewRequest(content, keywords, null);

        CreateClubReviewResponse response = reviewService.createReview(club.getId(), request);

        Optional<Review> createdReview = reviewRepository.findById(
                response.getReviewId());

        assertAll(
                () -> assertThat(createdReview).isNotNull(),
                () -> assertThat(createdReview.get().isDeleted()).isEqualTo(false),
                () -> assertThat(createdReview.get().getContent()).isEqualTo(content),
                () -> assertThat(createdReview.get().getReportStatus()).isEqualTo(ReportStatus.VISIBLE)
        );
    }

    @DisplayName("리뷰 삭제에 성공한다")
    @Test
    void deleteReviewSuccess() {
        User user = UserFixture.aUser().build();
        User savedUser = userRepository.save(user);
        createSecurityContext(savedUser);

        Club club = ClubFixture.aClub().isAgreeToReview(true).build();
        Club savedClub = clubRepository.save(club);

        Review review = ReviewFixture.aReview().user(savedUser).club(savedClub).build();
        Review savedReview = reviewRepository.save(review);

        reviewService.deleteReview(savedReview.getId());
        Review deletedReview = reviewRepository.findById(savedReview.getId()).get();

        assertAll(
                () -> assertThat(deletedReview).isNotNull(),
                () -> assertThat(deletedReview.isDeleted()).isEqualTo(true)
        );
    }
}
