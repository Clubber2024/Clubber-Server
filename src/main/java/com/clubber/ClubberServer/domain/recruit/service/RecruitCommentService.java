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
import com.clubber.ClubberServer.domain.recruit.implement.RecruitCommentReader;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitValidator;
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
    private final RecruitCommentReader recruitCommentReader;
    private final RecruitValidator recruitValidator;

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
        List<RecruitComment> comments = recruitCommentReader.findByRecruit(recruit);

        RecruitCommentVO recruitCommentVO = new RecruitCommentVO();
        for (RecruitComment comment : comments) {
            GetRecruitCommentResponse nowCommentResponse = GetRecruitCommentResponse.from(comment);

            recruitCommentVO.addToTreeStructure(nowCommentResponse);
            recruitCommentVO.updateInCommentResponse(comment.getParentComment(), nowCommentResponse);
        }
        return recruitCommentVO.getTotalComments();
    }

    @Transactional
    public DeleteRecruitCommentResponse deleteRecruitComment(Long recruitId, Long commentId) {
        User user = userReader.getCurrentUser();
        Recruit recruit = recruitReader.findRecruitById(recruitId);

        RecruitComment recruitComment = recruitCommentReader.findByIdAndRecruit(commentId, recruit);

        recruitValidator.validateCommentUser(recruitComment, user);
        recruitCommentAppender.delete(recruitComment);

        return DeleteRecruitCommentResponse.from(recruitComment);
    }
}
