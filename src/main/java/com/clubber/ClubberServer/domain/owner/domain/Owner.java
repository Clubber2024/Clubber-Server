package com.clubber.ClubberServer.domain.owner.domain;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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

    @Builder
    private Owner(Long id,String accountName,String password){
        this.id=id;
        this.accountName=accountName;
        this.password=password;
    }
}
