package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "club_info_idx_total_view_desc", columnList = "total_view desc"))
public class ClubInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instagram;

    private String youtube;

    private String leader;

    private Long room;

    private Long totalView;

    @Column(length = 1500)
    private String activity;

    public void increaseTotalView() {
        this.totalView++;
    }

    public void updateClubInfo(String instagram, String youtube, String leader, String activity,
        Long room) {
        this.instagram = instagram;
        this.youtube = youtube;
        this.leader = leader;
        this.activity = activity;
        this.room = room;
    }

    @Builder
    private ClubInfo(Long id, String instagram, String youtube, String leader, Long room,
        Long totalView,
        String activity) {
        this.id = id;
        this.instagram = instagram;
        this.youtube = youtube;
        this.leader = leader;
        this.room = room;
        this.totalView = totalView;
        this.activity = activity;
    }

}
