package com.clubber.ClubberServer.domain.owner.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String accountName;

    @NotNull
    private String password;

    @OneToMany
    @JoinColumn(name="notice_id")
    private List<Notice> notices =new ArrayList<>();

    @Builder
    private Owner(Long id,String accountName,String password,List<Notice> notices){
        this.id=id;
        this.accountName=accountName;
        this.password=password;
        this.notices=notices;
    }
}
