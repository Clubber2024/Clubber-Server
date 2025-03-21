package com.clubber.ClubberServer.integration.domain.recruit.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecruitCalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("유효하지_않은_년도인_경우_실패")
    void getRecruitCalendarByInvalidYear() throws Exception {
        mockMvc.perform(get("/api/v1/calendar")
                .param("year", "2024")
                .param("month", "12"))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("유효하지_않은_월인_경우_실패")
    void getRecruitCalendarByInvalidMonth() throws Exception {
        mockMvc.perform(get("/api/v1/calendar")
                .param("year", "2025")
                .param("month", "13"))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("유효한_날짜인_경우_성공")
    void getRecruitCalendarByValidDate() throws Exception {
        mockMvc.perform(get("/api/v1/calendar")
                .param("year", "2025")
                .param("month", "3"))
            .andExpect(status().is2xxSuccessful());

    }
}

