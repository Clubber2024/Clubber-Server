package com.clubber.ClubberServer.integration.domain.recruit.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecruitCalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @DisplayName("DTO의_startAt_endAt_yyyy-MM-dd HH:mm:ss_형식인지_검증")
    void checkStartAtEndAtFormat() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/calendar")
                .param("year", "2025")
                .param("month", "2"))
            .andExpect(status().is2xxSuccessful())
            .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        String datePattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";

        rootNode.get("data").get("recruitList").forEach(recruit -> {
            assertThat(recruit.get("startAt").asText()).matches(datePattern);
            assertThat(recruit.get("endAt").asText()).matches(datePattern);
        });

    }
}

