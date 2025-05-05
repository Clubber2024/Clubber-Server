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
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitCommentRepository;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.implement.UserReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Transactional
    public PostRecruitCommentResponse postRecruitComment(Long recruitId,
        PostRecruitCommentRequest request) {
        User user = userReader.getCurrentUser();
        Recruit recruit = recruitReader.findRecruitById(recruitId);

        RecruitComment parentComment = null;
        if (request.getParentId() != null) {
            parentComment = recruitCommentRepository.findById(request.getParentId())
                .orElseThrow(() -> RecruitCommentNotFoundException.EXCEPTION);
        }

        RecruitComment newComment = RecruitComment.of(recruit, user, request.getContent(), parentComment);
        RecruitComment savedComment = recruitCommentRepository.save(newComment);

        return PostRecruitCommentResponse.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<GetRecruitCommentResponse> getRecruitComment(Long recruitId) {

        Recruit recruit = recruitRepository.queryRecruitsById(recruitId)
            .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);

        List<RecruitComment> comments = recruitCommentRepository.findByRecruitOrderByIdAsc(
            recruit);

        List<GetRecruitCommentResponse> totalComments = new ArrayList<>();
        Map<Long, GetRecruitCommentResponse> commentMap = new HashMap<>();

        for (RecruitComment comment : comments) {
            GetRecruitCommentResponse oneComment = GetRecruitCommentResponse.from(comment);
            commentMap.put(oneComment.getCommentId(), oneComment);

            if (comment.getParentComment() == null) {
                totalComments.add(oneComment);
            } else {
                commentMap.get(comment.getParentComment().getId()).getReplies()
                    .add(oneComment);
            }
        }
        return totalComments;
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
