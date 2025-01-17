package com.clubber.ClubberServer.recruit.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class RecruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("관리자가_아닌경우_모집글작성_실패")
    @WithMockUser(username = "user")
    void createRecruitByUser() throws Exception{

        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\": \"hihi\", \"content\": \"hihi\", \"imageKey\": [\"imageKey1\", \"imageKey2\"]}"))
             .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("title이_null일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithNullTitle() throws Exception{

        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\": null, \"content\": \"hihi\", \"imageKey\": [\"imageKey1\", \"imageKey2\"]}"))
             .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("content이_null일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithNullContent() throws Exception{

        mockMvc.perform(post("/api/v1/admins/recruits")
               .contentType(MediaType.APPLICATION_JSON)
               .characterEncoding("UTF-8")
               .content("{\"title\": \"hihi\",  \"content\": null, \"imageKey\": [\"imageKey1\", \"imageKey2\"]}"))
             .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("title이_\"\"일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithEmptyTitle() throws Exception{

        mockMvc.perform(post("/api/v1/admins/recruits")
               .contentType(MediaType.APPLICATION_JSON)
               .characterEncoding("UTF-8")
              .content("{\"title\": \"\", \"content\": \"hihi\", \"imageKey\": [\"imageKey1\", \"imageKey2\"]}"))
             .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("content가_\"\"일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithEmptyContent() throws Exception{

        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\": \"모집글 제목\", \"content\": \"\", \"imageKey\": [\"imageKey1\", \"imageKey2\"]}"))
               .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("title이_\" \"일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithSpaceTitle() throws Exception{

        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\": \" \", \"content\": \"hihi\", \"imageKey\": [\"imageKey1\", \"imageKey2\"]}"))
              .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("content가_\" \"일때_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithSpaceContent() throws Exception{

        mockMvc.perform(post("/api/v1/admins/recruits")
               .contentType(MediaType.APPLICATION_JSON)
               .characterEncoding("UTF-8")
               .content("{\"title\": \"모집글 제목\", \"content\": \" \", \"imageKey\": [\"imageKey1\", \"imageKey2\"]}"))
              .andExpect(status().isBadRequest());

    }

}
