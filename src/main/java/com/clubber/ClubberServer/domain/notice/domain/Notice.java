package com.clubber.ClubberServer.domain.notice.domain;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.owner.domain.Owner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String imageurl;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Builder
    private Notice(Long id,String title,String content,String imageurl,Owner owner){
        this.id=id;
        this.title=title;
        this.content=content;
        this.imageurl=imageurl;
        this.owner=owner;
    }
}
