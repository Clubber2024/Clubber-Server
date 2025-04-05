package com.clubber.ClubberServer.domain.admin.domain;

import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
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
    private ClubType clubType;

    private College college = College.ETC;

    private Department department = Department.ETC;

    @NotNull
    private String clubName;

    @NotNull
    private String email;

    @Embedded
    private Contact contact;

    private String imageForApproval;

    private boolean isApproved = false;

    @Builder
    public PendingAdminInfo(Long id, String username, String password, String clubName, ClubType clubType, College college, Department department, String email, Contact contact, String imageForApproval) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.clubType = clubType;
        this.clubName = clubName;
        this.department = department;
        this.college = college;
        this.email = email;
        this.contact = contact;
        this.imageForApproval = imageForApproval;
    }
}
