package com.clubber.ClubberServer.admin.controller;

import static com.clubber.ClubberServer.util.fixture.AdminFixture.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.clubber.ClubberServer.util.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("동아리 활동 1500자 초과 시 에러발생")
	@WithMockCustomUser
	@Test
	void updateOverMaxSizeActiivty() throws Exception {
		String updateClubPage = objectMapper.writeValueAsString(OVER_MAX_LENGTH_ACTIVITY_UPDATE_PAGE_REQUEST);
		mockMvc.perform(patch("/api/v1/admins/change-page")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateClubPage))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.reason", containsString("최대 1500자까지 작성 가능합니다.")));
	}

	@DisplayName("동아리 소개 100자 초과 시 에러발생")
	@WithMockCustomUser
	@Test
	void updateOverMaxSizeIntroduction() throws Exception {
		String updateClubPage = objectMapper.writeValueAsString(OVER_MAX_LENGTH_INTRODUCTION_UPDATE_PAGE_REQUEST);
		mockMvc.perform(patch("/api/v1/admins/change-page")
						.contentType(MediaType.APPLICATION_JSON)
						.content(updateClubPage))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.reason", containsString("최대 100자까지 작성 가능합니다.")));
	}
}
