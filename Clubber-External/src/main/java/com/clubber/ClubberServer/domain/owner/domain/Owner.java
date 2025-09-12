package com.clubber.ClubberServer.domain.owner.domain;

import com.clubber.domain.common.BaseEntity;
import com.clubber.domain.domains.user.domain.AccountRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String accountName;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private AccountRole accountRole = AccountRole.SUPER_ADMIN;

    @Builder
    private Owner(Long id,String accountName,String password){
        this.id=id;
        this.accountName=accountName;
        this.password=password;
    }
}
