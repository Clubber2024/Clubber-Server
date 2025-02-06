package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.DeleteRecruitCommentResponse;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.GetRecruitCommentResponse;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.PostRecruitCommentRequest;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.PostRecruitCommentResponse;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitCommentRepository;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.service.UserReadService;
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

    private final UserReadService userReadService;
    private final RecruitRepository recruitRepository;
    private final RecruitCommentRepository recruitCommentRepository;

    @Transactional
    public PostRecruitCommentResponse postRecruitComment(Long recruitId,
        PostRecruitCommentRequest request) {

        User user = userReadService.getUser();

        // 모집글 있는지 확인
        Recruit recruit = recruitRepository.queryRecruitsById(recruitId)
            .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);

        // 부모 댓글 조회
        RecruitComment parentComment = null;
        if (request.getParentId() != null) {
            parentComment = recruitCommentRepository.findById(request.getParentId())
                .orElseThrow(() -> new RuntimeException("부모 댓글이 존재하지 않습니다."));
        }

        // 리뷰를 모집글에 등록
        RecruitComment newComment = RecruitComment.of(recruit, user, request, parentComment);
        RecruitComment savedComment = recruitCommentRepository.save(newComment);

        return PostRecruitCommentResponse.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<GetRecruitCommentResponse> getRecruitComment(Long recruitId) {

        // 모집글 있는지 확인
        Recruit recruit = recruitRepository.queryRecruitsById(recruitId)
            .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);

        // 모집글에 대한 모든 댓글 조회
        List<RecruitComment> comments = recruitCommentRepository.findByRecruitOrderByIdAsc(
            recruit);

        // 댓글을 계층 구조로 변환 (부모-자식 관계 정리)
        List<GetRecruitCommentResponse> totalComments = new ArrayList<>();
        Map<Long, GetRecruitCommentResponse> commentMap = new HashMap<>();

        for (RecruitComment comment : comments) {
            GetRecruitCommentResponse oneComment = GetRecruitCommentResponse.from(comment);
            commentMap.put(oneComment.getCommentId(), oneComment);

            if (comment.getParentComment() == null) {
                totalComments.add(oneComment); // 댓글인 경우 totalComments에 바로 추가
            } else {
                commentMap.get(comment.getParentComment().getId()).getReplies()
                    .add(oneComment); // 자신이 대댓글인 경우
            }
        }
        return totalComments;
    }

    @Transactional
    public DeleteRecruitCommentResponse deleteRecruitComment(Long recruitId, Long commentId) {
        User user = userReadService.getUser();

        Recruit recruit = recruitRepository.queryRecruitsById(recruitId)
            .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);

        RecruitComment recruitComment = recruitCommentRepository.findByIdAndRecruitAndIsDeletedFalse(
            commentId, recruit).orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다.")); // 앞선 pr이랑 충돌날까바 일단 이렇게 함

        if (!recruitComment.getUser().equals(user)) {
            throw new RuntimeException("댓글 작성자가 아닙니다."); // 앞선 pr이랑 충돌날까바 일단 이렇게 함
        }

        recruitComment.delete();

        return DeleteRecruitCommentResponse.from(recruitComment);

    }


}
