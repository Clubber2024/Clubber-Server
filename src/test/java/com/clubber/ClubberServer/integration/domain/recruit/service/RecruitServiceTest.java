package com.clubber.ClubberServer.integration.domain.recruit.service;


import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.IMAGE_SERVER;
import static com.clubber.ClubberServer.integration.util.fixture.RecruitFixture.INVALID_DELETE_IMAGE_RECRUIT_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.RecruitFixture.NEW_FINAL_IMAGE_DIFFERENT_RECRUIT_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.RecruitFixture.NEW_NOT_IN_FINAL_IMAGE_RECRUIT_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.RecruitFixture.REMAIN_DELETE_DUPLICATED_RECRUIT_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.RecruitFixture.REMAIN_NOT_EXIST_RECRUIT_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.RecruitFixture.UPDATE_NO_IMAGE_RECRUIT_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.RecruitFixture.VALID_UPDATE_RECRUIT_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import com.clubber.ClubberServer.domain.recruit.dto.UpdateRecruitResponse;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitImageDeleteRemainDuplicatedException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitImageNotFoundException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitImageRevisedFinalSizeException;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class RecruitServiceTest extends ServiceTest {

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private RecruitService recruitService;
    @PersistenceContext
    private EntityManager entityManager;

    @Nested
    @DisplayName("모집글 수정 성공 케이스")
    class SuccessCase {

        @Nested
        @DisplayName("이미지가 포함되어있는 모집글 수정")
        class ImageIncludedRecruitCase {

            @Test
            @DisplayName("모집글_제목_내용_수정_성공")
            @WithMockCustomUser
            void updateRecruitTitleContent() {

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
            @DisplayName("모집글_이미지_수정_성공")
            @WithMockCustomUser
            void updateRecruitWithInvalidTitle() {

                UpdateRecruitResponse updateRecruitResponse = recruitService.changeAdminRecruits(1L,
                    VALID_UPDATE_RECRUIT_REQUEST);

//                entityManager.flush();
//                entityManager.clear();

                Optional<Recruit> updatedRecruit = recruitRepository.findById(
                    updateRecruitResponse.getRecruitId());

                updatedRecruit.ifPresent(recruit -> {
                    recruit.getRecruitImages().forEach(image ->
                        System.out.println("Image URL: " + image.getImageUrl())
                    );
                });

                assertAll(
                    () -> assertThat(updatedRecruit).isNotNull(),
                    () -> assertFalse(updatedRecruit.get().getRecruitImages()
                        .contains("https://image.ssuclubber.com/image2")), // 삭제한 이미지
                    () -> assertThat(updatedRecruit.get().getRecruitImages()
                        .contains("https://image.ssuclubber.com/newImage1")), // 추가한 이미지1
                    () -> assertThat(updatedRecruit.get().getRecruitImages()
                        .contains("https://image.ssuclubber.com/newImage2")), // 추가한 이미지2
                    () -> assertThat(updatedRecruit.get().getRecruitImages()
                        .contains("https://image.ssuclubber.com/image1")) // 유지한 이미지
                );
            }

            @Test
            @DisplayName("모집글_새로_등록된_사진_개수_확인")
            @WithMockCustomUser
            void updateRecruitCheckImageNum() {

                UpdateRecruitResponse updateRecruitResponse = recruitService.changeAdminRecruits(1L,
                    VALID_UPDATE_RECRUIT_REQUEST);

//                entityManager.flush();
//                entityManager.clear();

                Optional<Recruit> updatedRecruit = recruitRepository.findById(
                    updateRecruitResponse.getRecruitId());

                assertAll(
                    () -> assertThat(updatedRecruit).isNotNull(),
                    () -> assertEquals(3,
                        updatedRecruit.get().getRecruitImages().stream()
                            .filter(image -> !image.isDeleted())
                            .count()
                    )
                );
            }

            @Test
            @DisplayName("모집글_사진_순서_처리_성공")
            @WithMockCustomUser
            void updateRecruitInOrder() {

                UpdateRecruitResponse updateRecruitResponse = recruitService.changeAdminRecruits(1L,
                    VALID_UPDATE_RECRUIT_REQUEST);

                entityManager.flush();
                entityManager.clear();

                Optional<Recruit> updatedRecruit = recruitRepository.queryRecruitsById(
                    updateRecruitResponse.getRecruitId());

                // 새로 지정한 순서
                List<String> updatedRecruitImages = VALID_UPDATE_RECRUIT_REQUEST.getImages()
                    .stream()
                    .map(
                        image -> image.startsWith(IMAGE_SERVER) ? image.substring(
                            IMAGE_SERVER.length())
                            : image)
                    .collect(Collectors.toList());

                // 실제 저장된 순서 - orderNum기준으로 오름차순 정렬
                List<String> sortedRecruitImageUrls = updatedRecruit.get().getRecruitImages()
                    .stream()
                    .filter(recruitImage -> !recruitImage.isDeleted())
                    .sorted(Comparator.comparingLong(RecruitImage::getOrderNum))
                    .map(recruitImage -> recruitImage.getImageUrl().getImageUrl())
                    .collect(Collectors.toList());

                assertAll(
                    () -> assertThat(updatedRecruit).isNotNull(),
                    () -> assertEquals(sortedRecruitImageUrls, updatedRecruitImages)
                );

            }
        }

        @Nested
        @DisplayName("이미지가 포함되지않은 모집글 수정")
        class ImageExcludedRecruitCase {

            @Test
            @DisplayName("이미지_추가_성공")
            @WithMockCustomUser
            void updateRecruitWithImage() {

                UpdateRecruitResponse updateRecruitResponse = recruitService.changeAdminRecruits(
                    2L,
                    UPDATE_NO_IMAGE_RECRUIT_REQUEST);
//
//                entityManager.flush();
//                entityManager.clear();

                Optional<Recruit> updatedRecruit = recruitRepository.findById(
                    updateRecruitResponse.getRecruitId());

                // 새로 지정한 순서
                List<String> updatedRecruitImages = UPDATE_NO_IMAGE_RECRUIT_REQUEST.getImages()
                    .stream()
                    .map(
                        image -> image.startsWith(IMAGE_SERVER) ? image.substring(
                            IMAGE_SERVER.length())
                            : image)
                    .collect(Collectors.toList());

                // 실제 저장된 순서 - orderNum기준으로 오름차순 정렬
                List<String> sortedRecruitImageUrls = updatedRecruit.get().getRecruitImages()
                    .stream()
                    .filter(recruitImage -> !recruitImage.isDeleted())
                    .sorted(Comparator.comparingLong(RecruitImage::getOrderNum))
                    .map(recruitImage -> recruitImage.getImageUrl().getImageUrl())
                    .collect(Collectors.toList());

                assertAll(
                    () -> assertThat(updatedRecruit).isNotNull(),
                    () -> assertThat(updatedRecruit.get().getTitle()).isEqualTo(
                        INVALID_DELETE_IMAGE_RECRUIT_REQUEST.getTitle()),
                    () -> assertThat(updatedRecruit.get().getContent()).isEqualTo(
                        INVALID_DELETE_IMAGE_RECRUIT_REQUEST.getContent()),
                    () -> assertEquals(sortedRecruitImageUrls, updatedRecruitImages));
            }
        }
    }


    @Nested
    @DisplayName("모집글 수정 실패 케이스")
    class FailCase {

        @Nested
        @DisplayName("이미지가 포함되어있는 모집글 수정")
        class ImageIncludedRecruitCase {

            @Test
            @DisplayName("삭제할 이미지가 기존에 등록된 이미지에 포함되지 않는 경우 실패")
            @WithMockCustomUser
            void updateRecruitTitleContent() {

                assertThatThrownBy(() -> {
                    recruitService.changeAdminRecruits(1L,
                        INVALID_DELETE_IMAGE_RECRUIT_REQUEST);
                }).isInstanceOf(RecruitImageNotFoundException.class);
            }

            @Test
            @DisplayName("유지할 이미지가 삭제할 이미지에 포함되는 경우 실패")
            @WithMockCustomUser
            void remainRecruitImageDuplicateWithDeleteImage() {

                assertThatThrownBy(() -> {
                    recruitService.changeAdminRecruits(1L,
                        REMAIN_DELETE_DUPLICATED_RECRUIT_REQUEST);
                }).isInstanceOf(RecruitImageDeleteRemainDuplicatedException.class);
            }

            @Test
            @DisplayName("유지할 이미지가 기존에 등록된 이미지에 포함되지 않는 경우 실패")
            @WithMockCustomUser
            void remainRecruitImageNotExist() {

                assertThatThrownBy(() -> {
                    recruitService.changeAdminRecruits(1L,
                        REMAIN_NOT_EXIST_RECRUIT_REQUEST);
                }).isInstanceOf(RecruitImageNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("이미지가 포함되지않은 모집글 수정")
        class ImageExcludedRecruitCase {

            @Test
            @DisplayName("새로운 이미지와 최종 이미지 리스트 불일치시 실패")
            @WithMockCustomUser
            void newFinalImageDifferent() {
                assertThatThrownBy(() -> {
                    recruitService.changeAdminRecruits(2L,
                        NEW_FINAL_IMAGE_DIFFERENT_RECRUIT_REQUEST);
                }).isInstanceOf(RecruitImageNotFoundException.class);
            }

            @Test
            @DisplayName("새로운 이미지가 최종 이미지 리스트에 없을때 실패")
            @WithMockCustomUser
            void newImageNotInFinalImageList() {
                assertThatThrownBy(() -> {
                    recruitService.changeAdminRecruits(2L,
                        NEW_NOT_IN_FINAL_IMAGE_RECRUIT_REQUEST);
                }).isInstanceOf(RecruitImageRevisedFinalSizeException.class);
            }
        }
    }

}
