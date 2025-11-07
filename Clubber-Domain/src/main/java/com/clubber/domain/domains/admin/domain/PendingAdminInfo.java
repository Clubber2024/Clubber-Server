package com.clubber.domain.domains.admin.domain;

import com.clubber.domain.common.BaseEntity;
import com.clubber.domain.domains.club.domain.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Builder
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PendingAdminInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ClubType clubType = ClubType.ETC;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private College college = College.ETC;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    private Department department = Department.ETC;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Division division = Division.ETC;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Hashtag hashtag = Hashtag.ETC;

    @NotNull
    private String clubName;

    @NotNull
    private String email;

    @Embedded
    private Contact contact;

    private String imageForApproval;

    private boolean isApproved = false;

//    @Builder
//    public PendingAdminInfo(String username, String password, String clubName, ClubType clubType, College college, Department department, String email, Contact contact, String imageForApproval) {
//        this.username = username;
//        this.password = password;
//        this.clubType = clubType;
//        this.clubName = clubName;
//        this.department = department;
//        this.division = division;
//        this.hashtag = hashtag;
//        this.college = college;
//        this.email = email;
//        this.contact = contact;
//        this.imageForApproval = imageForApproval;
//    }
}
