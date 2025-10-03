package com.clubber.domain.domains.review.domain;

import com.clubber.domain.common.BaseEntity;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.exception.ReviewAlreadyDeletedException;
import com.clubber.domain.domains.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = @Index(name = "idx_review_club_id_approved_status_id_desc",
        columnList = "club_id, approved_status, id desc"))
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    private String content;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @NotNull
    @Builder.Default
    private ReportStatus reportStatus = ReportStatus.VISIBLE;

    @Builder.Default
    private boolean isDeleted = false;

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewKeyword> reviewKeywords = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @OneToOne(mappedBy = "review")
    private ReviewReply reviewReply;

    public static Review of(User user, Club club, String content) {
        return Review.builder()
                .user(user)
                .club(club)
                .content(content)
                .build();
    }

    //양방향 매핑 메서드
    public void addKeywords(List<Keyword> keywords) {
        keywords.forEach(keyword -> {
            ReviewKeyword reviewKeyword = ReviewKeyword.of(keyword, this);
            this.reviewKeywords.add(reviewKeyword);
        });
    }

    public void delete() {
        if (isDeleted) {
            throw ReviewAlreadyDeletedException.EXCEPTION;
        }
        this.isDeleted = true;
    }

    public void hide() {
        this.reportStatus = ReportStatus.HIDDEN;
    }

    public String getContentForPublic() {
        if (reportStatus == ReportStatus.HIDDEN) {
            return null;
        }
        return content;
    }
}
