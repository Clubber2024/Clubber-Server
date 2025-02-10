package com.clubber.ClubberServer.integration.domain.recruit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.clubber.ClubberServer.integration.util.fixture.RecruitFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("관리자가_아닌경우_모집글작성_실패")
    @WithMockUser(username = "user")
    void createRecruitByUser() throws Exception {
        String recruit = objectMapper.writeValueAsString(RecruitFixture.VALID_RECRUIT_POST_REQUEST);
        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(recruit))
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("title이_null일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithNullTitle() throws Exception {
        String recruit = objectMapper.writeValueAsString(
            RecruitFixture.NULL_TITLE_RECRUIT_POST_REQUEST);
        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(recruit))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("title이_\"\"일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithEmptyTitle() throws Exception {
        String recruit = objectMapper.writeValueAsString(
            RecruitFixture.EMPTY_TITLE_RECRUIT_POST_REQUEST);
        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(recruit))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("title이_\" \"일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithSpaceTitle() throws Exception {
        String recruit = objectMapper.writeValueAsString(
            RecruitFixture.SPACE_TITLE_RECRUIT_POST_REQUEST);
        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(recruit))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("content이_null일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithNullContent() throws Exception {
        String recruit = objectMapper.writeValueAsString(
            RecruitFixture.NULL_CONTENT_RECRUIT_POST_REQUEST);
        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(recruit))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("content가_\"\"일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithEmptyContent() throws Exception {
        String recruit = objectMapper.writeValueAsString(
            RecruitFixture.EMPTY_CONTENT_RECRUIT_POST_REQUEST);
        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(recruit))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("content가_\" \"일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithSpaceContent() throws Exception {
        String recruit = objectMapper.writeValueAsString(
            RecruitFixture.SPACE_CONTENT_RECRUIT_POST_REQUEST);
        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(recruit))
            .andExpect(status().isBadRequest());

    }

}
