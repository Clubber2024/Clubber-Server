package com.clubber.domain.recruit.implement;

import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.recruit.domain.Recruit;
import com.clubber.domain.recruit.domain.RecruitComment;
import com.clubber.domain.recruit.domain.RecruitType;
import com.clubber.domain.recruit.exception.RecruitCommentUserUnauthorizedException;
import com.clubber.domain.recruit.exception.RecruitDeleteUnauthorizedException;
import com.clubber.domain.recruit.exception.RecruitInvalidPeriodException;
import com.clubber.domain.recruit.exception.RecruitMissingPeriodException;
import com.clubber.domain.recruit.exception.RecruitPeriodNotAllowedException;
import com.clubber.domain.domains.user.domain.User;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class RecruitValidator {

    public void validateCommentUser(RecruitComment recruitComment, User currentUser) {
        if (!recruitComment.getUser().equals(currentUser)) {
            throw RecruitCommentUserUnauthorizedException.EXCEPTION;
        }
    }

    public void validateRecruitClub(Recruit recruit, Admin admin) {
        if (recruit.getClub() != admin.getClub()) {
            throw RecruitDeleteUnauthorizedException.EXCEPTION;
        }
    }

    public void validateRecruitDate(RecruitType recruitType, LocalDateTime startAt,
        LocalDateTime endAt) {
        if (recruitType == RecruitType.ALWAYS) {
            if (startAt != null || endAt != null) {
                throw RecruitPeriodNotAllowedException.EXCEPTION;
            }
        } else {
            if (startAt == null || endAt == null) {
                throw RecruitMissingPeriodException.EXCEPTION;
            }
            if (startAt.isAfter(endAt)) {
                throw RecruitInvalidPeriodException.EXCEPTION;
            }
        }
    }
}
