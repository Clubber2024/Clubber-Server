package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCommentNotFoundException;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
}
