package com.clubber.ClubberServer.admin.controller;

import com.clubber.ClubberServer.util.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.clubber.ClubberServer.util.fixture.AdminReviewFixture.UPDATE_ADMINS_EMPTY_REVIEWS;
import static com.clubber.ClubberServer.util.fixture.AdminReviewFixture.UPDATE_ADMINS_OVER_MAX_REVIEWS;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class AdminReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("리뷰 승인/거절 목록이 빈 리스트라면 예외가 발생한다.")
    @WithMockCustomUser
    @Test
    void updateEmptyReviewStatus() throws Exception {
        String updateEmptyReviewStatus = objectMapper.writeValueAsString(UPDATE_ADMINS_EMPTY_REVIEWS);
        mockMvc.perform(patch("/api/v1/admins/reviews/decision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmptyReviewStatus))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", containsString("1개 이상 수정해야합니다")));
    }

    @DisplayName("리뷰 승인/거절 목록이 10개 초과일 경우 예외가 발생한다..")
    @WithMockCustomUser
    @Test
    void updateReviewOverMaxSize() throws Exception {
        String updateEmptyReviewStatus = objectMapper.writeValueAsString(UPDATE_ADMINS_OVER_MAX_REVIEWS);
        mockMvc.perform(patch("/api/v1/admins/reviews/decision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmptyReviewStatus))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", containsString("10개 이하로 수정해야합니다")));
    }
}
