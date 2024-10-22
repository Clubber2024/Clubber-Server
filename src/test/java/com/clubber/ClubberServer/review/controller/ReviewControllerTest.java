package com.clubber.ClubberServer.review.controller;

import static com.clubber.ClubberServer.util.fixture.ReviewFixture.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.clubber.ClubberServer.domain.auth.service.helper.CookieHelper;
import com.clubber.ClubberServer.domain.review.controller.ReviewController;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.user.domain.SnsType;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.global.config.security.JwtExceptionFilter;
import com.clubber.ClubberServer.global.config.security.JwtTokenFilter;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import com.clubber.ClubberServer.util.DatabaseCleaner;
import com.clubber.ClubberServer.util.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class ReviewControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CookieHelper cookieHelper;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Test
	@DisplayName("빈키워드_리뷰_예외발생")
	@WithMockCustomUser(first = "10000001", second = "USER")
	void emptyKeyword() throws Exception {
		String review = objectMapper.writeValueAsString(EMPTY_KEYWORD_REVIEW_REQUEST);
		mockMvc.perform(post("/api/v1/clubs/1/reviews")
				.contentType(MediaType.APPLICATION_JSON)
				.headers(createUserCookie())
				.content(review))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.reason", containsString("1개 이상의 키워드를 선택해주세요")));
	}

	@Test
	@DisplayName("100자보다_긴_리뷰_예외발생")
	@WithMockCustomUser(first = "10000001", second = "USER")
	void longInvalidReview() throws Exception {
		String review = objectMapper.writeValueAsString(LONG_SIZE_INVALID_REVIEW_REQUEST);

		mockMvc.perform(post("/api/v1/clubs/1/reviews")
				.contentType(MediaType.APPLICATION_JSON)
				.headers(createUserCookie())
				.content(review))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.reason", containsString("리뷰 작성은 100자까지 가능합니다")));
	}

	HttpHeaders createUserCookie() {
		User user = User.builder().id(10000001L).snsId(1L).email("email").snsType(SnsType.KAKAO).build();
		String accessToken = jwtTokenProvider.generateAccessToken(user);
		String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
		return cookieHelper.getCookies(accessToken, refreshToken);
	}
}
