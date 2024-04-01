package com.clubber.ClubberServer.domain.club.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotNull
    private String clubType;

    @Nullable
    private String division; //분과 (중앙동아리)

    @Nullable
    private String college; //단과대 (소모임)

    @Nullable
    private String department; //학과 (소모임)

    private Long totalView;

    @OneToMany(mappedBy = "club")
    private List<Review> reviews=new ArrayList<>();

    @Builder
    public Club(Long id, String name, String clubType,String division, String college, String department, Long totalView,List<Review> reviews) {
        this.id = id;
        this.name=name;
        this.clubType=clubType;
        this.division = division;
        this.college = college;
        this.department = department;
        this.totalView = totalView;
        this.reviews=reviews !=null ?reviews: new ArrayList<>();
    }
}