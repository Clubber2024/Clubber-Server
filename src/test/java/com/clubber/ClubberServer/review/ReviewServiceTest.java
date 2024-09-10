package com.clubber.ClubberServer.review;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Array;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewsWithContentResponse;
import com.clubber.ClubberServer.domain.review.dto.CreateReviewClubWithContentRequest;

import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.review.service.ReviewService;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.AuthDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

	@InjectMocks
	ReviewService reviewService;

	@Mock
	ReviewRepository reviewRepository;

	@Mock
	ClubRepository clubRepository;

	@Mock
	UserRepository userRepository;

	User user;

	Club club;

	@BeforeEach
	void setUp() {
		AuthDetails userDetails = new AuthDetails("1", "user");
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
		user = User.builder().id(1L).build();
		club = Club.builder().id(1L).isAgreeToReview(true).build();
	}

	@Test
	void 리뷰작성_성공() throws Exception{
		//given
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(clubRepository.findClubByIdAndIsDeleted(anyLong(), eq(false))).thenReturn(Optional.of(club));
		when(reviewRepository.existsByUserAndClub(any(), any())).thenReturn(false);

		/**
		 * request dto 생성자로 객체 생성 불가능 문제 추후 고민
		 */
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "{\n" +
			"  \"content\": \"활동이 유익해요\",\n" +
			"  \"keywords\": [\"FEE\", \"CULTURE\"]\n" +
			"}";
		CreateReviewClubWithContentRequest request = objectMapper.readValue(json, CreateReviewClubWithContentRequest.class);

		/**
		 * cascade 파트 테스트는 jpatest로 추가 검증해야할 지 추후 고민
		 */
		Review savedReview = Review.of(user, club, request.getContent());
		request.toReviewKeywordEntities(savedReview);
		when(reviewRepository.save(any())).thenReturn(savedReview);

		CreateClubReviewsWithContentResponse reviewResponse = reviewService.createReviewsByContent(club.getId(),
			request);

		Set<String> keywordTitles= new HashSet<>();
		keywordTitles.add(Keyword.CULTURE.getTitle());
		keywordTitles.add(Keyword.FEE.getTitle());

		assertEquals(reviewResponse.getContent(), request.getContent());
		assertEquals(reviewResponse.getKeywords(), keywordTitles);
	}


}
