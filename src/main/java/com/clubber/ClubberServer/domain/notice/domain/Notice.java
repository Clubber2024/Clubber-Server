package com.clubber.ClubberServer.domain.notice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String imageurl;

    @Builder
    private Notice(Long id,String title,String content,String imageurl){
        this.id=id;
        this.title=title;
        this.content=content;
        this.imageurl=imageurl;
    }
}
