package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.PostRecruitCommentRequest;
import com.clubber.ClubberServer.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private boolean isDeleted = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RecruitComment parentComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    public void delete() {
        this.isDeleted = true;
    }

    @Builder
    private RecruitComment(Long id, String content, User user, Recruit recruit,
        RecruitComment parentComment) {
        this.id = id;
        this.content = content;
        this.parentComment = parentComment;
        this.user = user;
        this.recruit = recruit;
    }

    public static RecruitComment of(Recruit recruit, User user,
        PostRecruitCommentRequest commentRequest, RecruitComment parentComment) {
        return RecruitComment.builder()
            .content(commentRequest.getContent())
            .parentComment(parentComment)
            .user(user)
            .recruit(recruit)
            .build();
    }

}
