package com.clubber.ClubberServer.integration.domain.club.controller;


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
public class ClubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("검색결과와 매칭되는 동아리가 없는 경우")
    @WithMockUser(username = "user")
    void searchClubWithNameNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/clubs")
                .param("clubName", "zz"))
            .andExpect(status().isNotFound());

    }


    @Test
    @DisplayName("존재하지 않는 해시태그인 경우 실패")
    @WithMockUser(username = "user")
    void searchClubWithNonExistHashtag() throws Exception {
        mockMvc.perform(get("/api/v1/clubs")
                .param("hashtag", "INVALID_HASHTAG"))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("존재하는 해시태그이지만 검색결과가 없는 경우")
    @WithMockUser(username = "user")
    void searchClubWithHashtagNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/clubs")
                .param("hashtag", "RELIGION"))
            .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("존재하지 않는 분과인 경우 실패")
    @WithMockUser(username = "user")
    void searchClubWithNonExistDivision() throws Exception {
        mockMvc.perform(get("/api/v1/clubs")
                .param("division", "INVALID_DIVISION"))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("존재하는 분과이지만 동아리가 없는 경우 실패")
    @WithMockUser(username = "user")
    void searchClubWithDivisionNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/clubs")
                .param("division", "SPORTS"))
            .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("존재하지 않는 단과대인 경우 실패")
    @WithMockUser(username = "user")
    void searchClubWithNonExistDepartment() throws Exception {
        mockMvc.perform(get("/api/v1/clubs")
                .param("department", "INVALID_DEPARTMENT"))
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("존재하는 단과대이지만 동아리가 없는 경우 실패")
    @WithMockUser(username = "user")
    void searchClubWithDepartmentNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/clubs")
                .param("department", "AI"))
            .andExpect(status().isNotFound());

    }

}
