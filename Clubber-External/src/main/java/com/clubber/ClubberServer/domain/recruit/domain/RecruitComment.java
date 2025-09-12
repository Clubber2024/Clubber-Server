package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.domain.common.BaseEntity;
import com.clubber.domain.domains.user.domain.User;
import jakarta.persistence.*;
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
        String content, RecruitComment parentComment) {
        return RecruitComment.builder()
            .content(content)
            .parentComment(parentComment)
            .user(user)
            .recruit(recruit)
            .build();
    }
}
