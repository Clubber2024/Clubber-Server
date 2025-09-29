package com.clubber.domain.domains.club.domain;

import com.clubber.domain.domains.club.exception.*;
import com.clubber.domain.common.BaseEntity;
import com.clubber.common.vo.image.ImageVO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Club extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ClubType clubType = ClubType.ETC;

    @Column(length = 1000)
    private String introduction;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Hashtag hashtag = Hashtag.ETC;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Division division = Division.ETC;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private College college = College.ETC;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Department department = Department.ETC;

    @Embedded
    private ImageVO imageUrl;

    private boolean isDeleted = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubInfo_id")
    private ClubInfo clubInfo;

    @Builder.Default
    private boolean isAgreeToReview = true;

    private boolean isAgreeToProvideInfo = false;

    public void updateClub(String imageKey, String introduction) {
        this.imageUrl = ImageVO.valueOf(imageKey);
        this.introduction = introduction;
    }

    public void delete() {
        if (this.isDeleted) {
            throw ClubAlreadyDeletedException.EXCEPTION;
        }
        this.isDeleted = true;
    }

    public void validateAgreeToReview() {
        if (!isAgreeToReview)
            throw ClubNotAgreeToProvideReviewException.EXCEPTION;
    }

    public void disableReview() {
        if (!isAgreeToReview) {
            throw ClubReviewAlreadyDisabledException.EXCEPTION;
        }
        this.isAgreeToReview = false;
    }

    public void enableReview() {
        if (isAgreeToReview) {
            throw ClubReviewAlreadyEnabledException.EXCEPTION;
        }
        this.isAgreeToReview = true;
    }
}