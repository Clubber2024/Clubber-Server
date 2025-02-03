package com.clubber.ClubberServer.integration.domain.recruit.service;


import static com.clubber.ClubberServer.integration.util.fixture.RecruitFixture.VALID_UPDATE_RECRUIT_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.dto.UpdateRecruitResponse;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RecruitServiceTest extends ServiceTest {

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private RecruitService recruitService;


    @Test
    @DisplayName("모집글_수정_성공")
    @WithMockCustomUser
    void updateRecruit() {

        UpdateRecruitResponse updateRecruitResponse = recruitService.changeAdminRecruits(1L,
            VALID_UPDATE_RECRUIT_REQUEST);

        Optional<Recruit> updatedRecruit = recruitRepository.findById(
            updateRecruitResponse.getRecruitId());

        assertAll(
            () -> assertThat(updatedRecruit).isNotNull(),
            () -> assertThat(updatedRecruit.get().getTitle()).isEqualTo(
                VALID_UPDATE_RECRUIT_REQUEST.getTitle()),
            () -> assertThat(updatedRecruit.get().getContent()).isEqualTo(
                VALID_UPDATE_RECRUIT_REQUEST.getContent())

        );

    }

    @Test
    @DisplayName("모집글_새로운사진 등록 확인")
    @WithMockCustomUser
    void updateRecruitWithInvalidTitle() {

        UpdateRecruitResponse updateRecruitResponse = recruitService.changeAdminRecruits(1L,
            VALID_UPDATE_RECRUIT_REQUEST);

        Optional<Recruit> updatedRecruit = recruitRepository.findById(
            updateRecruitResponse.getRecruitId());

        assertAll(
            () -> assertThat(updatedRecruit).isNotNull(),
            () -> assertFalse(updatedRecruit.get().getRecruitImages()
                .contains("https://image.ssuclubber.com/image2")), // 삭제한 것
            () -> assertThat(updatedRecruit.get().getRecruitImages()
                .contains("https://image.ssuclubber.com/newImage1")), // 추가한 이미지1
            () -> assertThat(updatedRecruit.get().getRecruitImages()
                .contains("https://image.ssuclubber.com/newImage2")), // 추가한 이미지2
            () -> assertThat(updatedRecruit.get().getRecruitImages()
                .contains("https://image.ssuclubber.com/image1")) // 유지한 이미지
        );

//        @Test
//        @DisplayName("모집글_사진_순서_체크_테스트)
//        @WithMockCustomUser
//            void 순서체크(){}


    }

}
