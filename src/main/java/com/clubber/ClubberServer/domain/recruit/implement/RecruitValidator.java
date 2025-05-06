package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCommentUserUnauthorizedException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitDeleteUnauthorizedException;
import com.clubber.ClubberServer.domain.user.domain.User;
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
}
