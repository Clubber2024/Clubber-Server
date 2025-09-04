package com.clubber.ClubberServer.integration.domain.recruitComment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.clubber.ClubberServer.integration.util.fixture.RecruitCommentFixture;
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
public class RecruitCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인된_상태가_아닌경우_댓글_작성_실패")
    void postRecruitCommentByNonUser() throws Exception {
        String comment = objectMapper.writeValueAsString(
            RecruitCommentFixture.VALID_COMMENT_REQUEST);
        mockMvc.perform(post("/api/v1/recruits/{recruitId}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(comment))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인된_상태가_아닌경우_댓글_작성_실패")
    void deleteRecruitCommentByNonUser() throws Exception {
        String comment = objectMapper.writeValueAsString(
            RecruitCommentFixture.VALID_COMMENT_REQUEST);
        mockMvc.perform(delete("/api/v1/recruits/{recruitId}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(comment))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("content가_null일때_댓글_작성_실패")
    @WithMockUser(username = "user")
    void postNullContentComment() throws Exception {
        String comment = objectMapper.writeValueAsString(
            RecruitCommentFixture.NULL_CONTENT_COMMENT_REQUEST);
        mockMvc.perform(post("/api/v1/recruits/{recruitId}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(comment))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("content가_비어있을때_댓글_작성_실패")
    @WithMockUser(username = "user")
    void postEmptyContentComment() throws Exception {
        String comment = objectMapper.writeValueAsString(
            RecruitCommentFixture.EMPTY_CONTENT_COMMENT_REQUEST);
        mockMvc.perform(post("/api/v1/recruits/{recruitId}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(comment))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("content가_공백문자열일때_댓글_작성_실패")
    @WithMockUser(username = "user")
    void postSpaceContentComment() throws Exception {
        String comment = objectMapper.writeValueAsString(
            RecruitCommentFixture.SPACE_CONTENT_COMMENT_REQUEST);
        mockMvc.perform(post("/api/v1/recruits/{recruitId}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(comment))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("100자가_넘는_댓글_작성_실패")
    @WithMockUser(username = "user")
    void postLengthContentComment() throws Exception {
        String comment = objectMapper.writeValueAsString(
            RecruitCommentFixture.INVALID_LENGTH_CONTENT_COMMENT_REQUEST);
        mockMvc.perform(post("/api/v1/recruits/{recruitId}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(comment))
            .andExpect(status().isBadRequest());
    }

}
