//package com.clubber.ClubberServer.integration.domain.review.controller;
//
//import static com.clubber.ClubberServer.integration.util.fixture.ReviewFixture.EMPTY_KEYWORD_REVIEW_REQUEST;
//import static com.clubber.ClubberServer.integration.util.fixture.ReviewFixture.LONG_SIZE_INVALID_REVIEW_REQUEST;
//import static org.hamcrest.Matchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("local")
//public class ReviewControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//
//	@Test
//	@DisplayName("빈키워드_리뷰_예외발생")
//	@WithMockCustomUser(second = "USER")
//	void emptyKeyword() throws Exception {
//		String review = objectMapper.writeValueAsString(EMPTY_KEYWORD_REVIEW_REQUEST);
//		mockMvc.perform(post("/api/v1/clubs/1/reviews")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(review))
//			.andExpect(status().isBadRequest())
//			.andExpect(jsonPath("$.reason", containsString("1개 이상의 키워드를 선택해주세요")));
//	}
//
//	@Test
//	@DisplayName("100자보다_긴_리뷰_예외발생")
//	@WithMockCustomUser(second = "USER")
//	void longInvalidReview() throws Exception {
//		String review = objectMapper.writeValueAsString(LONG_SIZE_INVALID_REVIEW_REQUEST);
//
//		mockMvc.perform(post("/api/v1/clubs/1/reviews")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(review))
//			.andExpect(status().isBadRequest())
//			.andExpect(jsonPath("$.reason", containsString("리뷰 작성은 100자까지 가능합니다")));
//	}
//}
