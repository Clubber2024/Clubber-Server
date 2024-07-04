package com.clubber.ClubberServer.domain.review.domain;

import com.clubber.ClubberServer.domain.admin.exception.InvalidApprovedStatusException;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    private ApprovedStatus approvedStatus;

    @Builder
    private Review(Long id, Club club, User user, String content) {
        this.id = id;
        this.club = club;
        this.user = user;
        this.content = content;
    }

    public static Review of(User user, Club club){
        return Review.builder()
                .user(user)
                .club(club)
                .build();
    }

    public void approve(){
        if(this.approvedStatus != ApprovedStatus.PENDING)
            throw InvalidApprovedStatusException.EXCEPTION;
        this.approvedStatus = ApprovedStatus.APPROVED;
    }

    public void reject() {
        if(this.approvedStatus != ApprovedStatus.PENDING)
            throw InvalidApprovedStatusException.EXCEPTION;
        this.approvedStatus = ApprovedStatus.REJECTED;
    }
}
