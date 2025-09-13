package com.clubber.domain.recruit.implement;

import com.clubber.domain.recruit.domain.Recruit;
import com.clubber.domain.recruit.domain.RecruitComment;
import com.clubber.domain.recruit.dto.recruitComment.PostRecruitCommentRequest;
import com.clubber.domain.recruit.repository.RecruitCommentRepository;
import com.clubber.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitCommentAppender {

    private final RecruitCommentRepository recruitCommentRepository;

    private final RecruitCommentReader recruitCommentReader;

    public RecruitComment append(PostRecruitCommentRequest request, Recruit recruit, User user) {
        RecruitComment parentComment = recruitCommentReader.findParentComment(request.getParentId());
        RecruitComment newComment = request.toEntity(recruit, user, parentComment);
        return recruitCommentRepository.save(newComment);
    }

    public void delete(RecruitComment recruitComment) {
        recruitComment.delete();
    }
}
