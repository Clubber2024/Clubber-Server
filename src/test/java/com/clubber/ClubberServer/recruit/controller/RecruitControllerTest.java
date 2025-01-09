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
    @DisplayName("title없이_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithNoTitle() throws Exception{

        mockMvc.perform(post("/api/v1/admins/recruits")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"title\": null, \"content\": \"hihi\", \"imageKey\": [\"imageKey1\", \"imageKey2\"]}"))
          .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("content없이_모집글작성_실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createRecruitWithNoContent() throws Exception{

        mockMvc.perform(post("/api/v1/admins/recruits")
              .contentType(MediaType.APPLICATION_JSON)
              .characterEncoding("UTF-8")
              .content("{\"title\": \"hihi\",  \"content\": null, \"imageKey\": [\"imageKey1\", \"imageKey2\"]}"))
           .andExpect(status().isBadRequest());

    }

//    @Test
//    @DisplayName("모집글_수정_성공")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void createRecruitSuccess() throws Exception{
//        String requestJson = """
//
//        {
//            "title": "Test title",
//            "content": "Test Content",
//            "deletedImageUrls": [
//                "https://example.com/image1.jpg"
//            ],
//            "newImageKeys": [
//                "key1",
//                "key2"
//            ],
//            "remainImageUrls": [
//                "https://example.com/image2.jpg",
//                "https://example.com/image3.jpg"
//            ],
//            "images": [
//                "key1",
//                "https://example.com/image3.jpg",
//                "https://example.com/image2.jpg",
//                "key2"
//            ]
//        }
//        """;
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/admins/recruits/{recruitId}",1)
//              .contentType(MediaType.APPLICATION_JSON)
//              .content(requestJson))
//           .andExpect(status().is2xxSuccessful());
//}

}
