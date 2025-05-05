package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.DeleteRecruitCommentResponse;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.GetRecruitCommentResponse;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.PostRecruitCommentRequest;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.PostRecruitCommentResponse;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCommentNotFoundException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCommentUserUnauthorizedException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitCommentAppender;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitCommentRepository;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.domain.recruit.vo.RecruitCommentVO;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.implement.UserReader;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitCommentService {

    private final UserReader userReader;
    private final RecruitRepository recruitRepository;
    private final RecruitCommentRepository recruitCommentRepository;
    private final RecruitReader recruitReader;
    private final RecruitCommentAppender recruitCommentAppender;

    @Transactional
    public PostRecruitCommentResponse postRecruitComment(Long recruitId,
        PostRecruitCommentRequest request) {
        User user = userReader.getCurrentUser();
        Recruit recruit = recruitReader.findRecruitById(recruitId);

        RecruitComment savedComment = recruitCommentAppender.append(request, recruit, user);
        return PostRecruitCommentResponse.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<GetRecruitCommentResponse> getRecruitComment(Long recruitId) {

        Recruit recruit = recruitReader.findRecruitById(recruitId);

        List<RecruitComment> comments = recruitCommentRepository.findByRecruitOrderByIdAsc(
            recruit);

        RecruitCommentVO recruitCommentVO = new RecruitCommentVO();
        for (RecruitComment comment : comments) {
            GetRecruitCommentResponse recruitCommentResponse = GetRecruitCommentResponse.from(comment);

            recruitCommentVO.addToTreeStructure(recruitCommentResponse);
            RecruitComment parentComment = comment.getParentComment();

            recruitCommentVO.updateInCommentResponse(parentComment, recruitCommentResponse);
        }
        return recruitCommentVO.getTotalComments();
    }

    @Transactional
    public DeleteRecruitCommentResponse deleteRecruitComment(Long recruitId, Long commentId) {
        User user = userReader.getCurrentUser();

        Recruit recruit = recruitRepository.queryRecruitsById(recruitId)
            .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);

        RecruitComment recruitComment = recruitCommentRepository.findByIdAndRecruitAndIsDeletedFalse(
            commentId, recruit).orElseThrow(() -> RecruitCommentNotFoundException.EXCEPTION);

        if (!recruitComment.getUser().equals(user)) {
            throw RecruitCommentUserUnauthorizedException.EXCEPTION;
        }

        recruitComment.delete();

        return DeleteRecruitCommentResponse.from(recruitComment);
    }

}
