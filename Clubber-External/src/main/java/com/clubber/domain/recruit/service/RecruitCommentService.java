package com.clubber.domain.recruit.service;

import com.clubber.domain.recruit.domain.Recruit;
import com.clubber.domain.recruit.domain.RecruitComment;
import com.clubber.domain.recruit.dto.recruitComment.DeleteRecruitCommentResponse;
import com.clubber.domain.recruit.dto.recruitComment.GetRecruitCommentResponse;
import com.clubber.domain.recruit.dto.recruitComment.PostRecruitCommentRequest;
import com.clubber.domain.recruit.dto.recruitComment.PostRecruitCommentResponse;
import com.clubber.domain.recruit.implement.RecruitCommentAppender;
import com.clubber.domain.recruit.implement.RecruitCommentReader;
import com.clubber.domain.recruit.implement.RecruitReader;
import com.clubber.domain.recruit.implement.RecruitValidator;
import com.clubber.domain.recruit.mapper.RecruitMapper;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitCommentService {

    private final UserReader userReader;
    private final RecruitReader recruitReader;
    private final RecruitCommentReader recruitCommentReader;
    private final RecruitCommentAppender recruitCommentAppender;
    private final RecruitValidator recruitValidator;
    private final RecruitMapper recruitMapper;

    @Transactional
    public PostRecruitCommentResponse postRecruitComment(Long recruitId, PostRecruitCommentRequest request) {
        User user = userReader.getCurrentUser();
        Recruit recruit = recruitReader.findRecruitById(recruitId);

        RecruitComment savedComment = recruitCommentAppender.append(request, recruit, user);
        return PostRecruitCommentResponse.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<GetRecruitCommentResponse> getRecruitComment(Long recruitId) {
        Recruit recruit = recruitReader.findRecruitById(recruitId);
        List<RecruitComment> comments = recruitCommentReader.findByRecruit(recruit);
        return recruitMapper.getRecruitCommentResponses(comments);
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
