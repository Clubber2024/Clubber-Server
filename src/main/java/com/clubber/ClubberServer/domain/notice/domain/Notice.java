package com.clubber.ClubberServer.domain.notice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String ownerId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="notice_id")
    private Owner owner;

    @Builder
    public Notice(Long id,String title,String content,String ownerId,Owner owner){
        this.id=id;
        this.title=title;
        this.content=content;
        this.ownerId=ownerId;
        this.owner=owner;
    }
}
