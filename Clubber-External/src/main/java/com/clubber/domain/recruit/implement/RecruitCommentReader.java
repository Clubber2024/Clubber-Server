package com.clubber.domain.recruit.implement;

import com.clubber.domain.recruit.domain.Recruit;
import com.clubber.domain.recruit.domain.RecruitComment;
import com.clubber.domain.recruit.exception.RecruitCommentNotFoundException;
import com.clubber.domain.recruit.repository.RecruitCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitCommentReader {
    private final RecruitCommentRepository recruitCommentRepository;

    public RecruitComment findParentComment(Long parentId) {
        if (parentId == null) {
            return null;
        }
        return recruitCommentRepository.findById(parentId)
                .orElseThrow(() -> RecruitCommentNotFoundException.EXCEPTION);
    }

    public RecruitComment findByIdAndRecruit(Long commentId, Recruit recruit) {
        return recruitCommentRepository.findByIdAndRecruitAndIsDeletedFalse(commentId, recruit)
                .orElseThrow(() -> RecruitCommentNotFoundException.EXCEPTION);
    }

    public List<RecruitComment> findByRecruit(Recruit recruit) {
        return recruitCommentRepository.findByRecruitOrderByIdAsc(recruit);
    }
}
