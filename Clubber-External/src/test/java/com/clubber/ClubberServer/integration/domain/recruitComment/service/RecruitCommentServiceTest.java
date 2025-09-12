package com.clubber.ClubberServer.integration.domain.recruitComment.service;

import static com.clubber.ClubberServer.integration.util.fixture.RecruitCommentFixture.NO_PARENT_REPLY_COMMENT_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.RecruitCommentFixture.VALID_COMMENT_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.RecruitCommentFixture.VALID_REPLY_COMMENT_REQUEST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.clubber.domain.recruit.domain.RecruitComment;
import com.clubber.domain.recruit.dto.recruitComment.DeleteRecruitCommentResponse;
import com.clubber.domain.recruit.dto.recruitComment.PostRecruitCommentResponse;
import com.clubber.domain.recruit.exception.RecruitCommentNotFoundException;
import com.clubber.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.domain.recruit.repository.RecruitCommentRepository;
import com.clubber.domain.recruit.service.RecruitCommentService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RecruitCommentServiceTest extends ServiceTest {

    @Autowired
    private RecruitCommentService recruitCommentService;

    @Autowired
    private RecruitCommentRepository recruitCommentRepository;

    @Nested
    @DisplayName("댓글 작성 성공 케이스")
    class PostSuccessCase {

        @DisplayName("댓글_작성_성공")
        @WithMockCustomUser(second = "USER")
        @Test
        void postCommentSuccess() {
            PostRecruitCommentResponse postRecruitCommentResponse = recruitCommentService.postRecruitComment(
                1L, VALID_COMMENT_REQUEST);

            Optional<RecruitComment> postedComment = recruitCommentRepository.findById(
                postRecruitCommentResponse.getCommentId()
            );

            assertAll(
                () -> assertThat(postedComment).isNotNull(),
                () -> assertThat(postedComment.get().getParentComment()).isNull(),
                () -> assertThat(postedComment.get().getContent()).isEqualTo(
                    VALID_COMMENT_REQUEST.getContent())
            );
        }

        @DisplayName("대댓글_작성_성공")
        @WithMockCustomUser(second = "USER")
        @Test
        void postReplyCommentSuccess() {
            PostRecruitCommentResponse postRecruitCommentResponse = recruitCommentService.postRecruitComment(
                1L, VALID_REPLY_COMMENT_REQUEST);

            Optional<RecruitComment> postedComment = recruitCommentRepository.findById(
                postRecruitCommentResponse.getCommentId()
            );

            assertAll(
                () -> assertThat(postedComment).isNotNull(),
                () -> assertThat(postedComment.get().getParentComment().getId()).isEqualTo(
                    VALID_REPLY_COMMENT_REQUEST.getParentId()),
                () -> assertThat(postedComment.get().getContent()).isEqualTo(
                    VALID_REPLY_COMMENT_REQUEST.getContent())
            );
        }
    }

    @Nested
    @DisplayName("댓글 작성 실패 케이스")
    class PostFailCase {

        @DisplayName("존재하지_않는_모집글인경우_실패")
        @WithMockCustomUser(second = "USER")
        @Test
        void postReplyCommentSuccess() {
            assertThatThrownBy(() -> {
                recruitCommentService.postRecruitComment(3L,
                    VALID_COMMENT_REQUEST);
            }).isInstanceOf(RecruitNotFoundException.class);
        }

        @DisplayName("부모댓글이_존재하지_않는경우_실패")
        @WithMockCustomUser(second = "USER")
        @Test
        void noCommentExistsToDelete() {
            assertThatThrownBy(() -> {
                recruitCommentService.postRecruitComment(1L,
                    NO_PARENT_REPLY_COMMENT_REQUEST);
            }).isInstanceOf(RecruitCommentNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("댓글 삭제 성공 케이스")
    class DeleteSuccessCase {

        @DisplayName("댓글 삭제 성공")
        @WithMockCustomUser(second = "USER")
        @Test
        void deleteCommentSuccess() {
            DeleteRecruitCommentResponse deleteSuccessCase = recruitCommentService.deleteRecruitComment(
                1L, 1L);

            Optional<RecruitComment> deletedComment = recruitCommentRepository.findById(
                deleteSuccessCase.getCommentId()
            );

            assertAll(
                () -> assertThat(deletedComment).isNotNull(),
                () -> assertThat(deletedComment.get().isDeleted()).isEqualTo(true)
            );
        }
    }

    @Nested
    @DisplayName("댓글 삭제 실패 케이스")
    class DeleteFailCase {

        @DisplayName("존재하지_않는_모집글인_경우_실패")
        @WithMockCustomUser(second = "USER")
        @Test
        void postReplyCommentSuccess() {
            assertThatThrownBy(() -> {
                recruitCommentService.deleteRecruitComment(3L,
                    1L);
            }).isInstanceOf(RecruitNotFoundException.class);
        }

        @DisplayName("이미_삭제된_댓글인_경우_실패")
        @WithMockCustomUser(second = "USER")
        @Test
        void noCommentExistsToDelete() {
            assertThatThrownBy(() -> {
                recruitCommentService.deleteRecruitComment(1L,
                    10L);
            }).isInstanceOf(RecruitCommentNotFoundException.class);
        }
    }
}



