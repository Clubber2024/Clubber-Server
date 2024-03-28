package com.clubber.ClubberServer.domain.club.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name="DTYPE") //동아리인지 소모임인지 구분하는 칼럼
public abstract class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long total_view;

    private byte[] logo_image;

    @Builder
    public Club(Long id, String name, Long totalView,byte[] logoImage) {
        this.id = id;
        this.name=name;
        this.total_view = totalView;
        this.logo_image=logoImage;
    }

}