package com.clubber.ClubberServer.domain.club.repository;

import com.clubber.ClubberServer.domain.club.domain.QClub;
import com.clubber.ClubberServer.domain.club.domain.QClubInfo;
import com.clubber.ClubberServer.domain.club.dto.GetClubPopularResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.clubber.ClubberServer.domain.club.domain.QClub.club;
import static com.clubber.ClubberServer.domain.club.domain.QClubInfo.clubInfo;

@RequiredArgsConstructor
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetClubPopularResponse> findAllOrderByTotalViewDesc() {
        return queryFactory.select(
                        Projections.constructor(
                                GetClubPopularResponse.class,
                                club.id,
                                club.name,
                                clubInfo.totalView
                        )).from(club).join(club.clubInfo, clubInfo)
                .orderBy(clubInfo.totalView.desc())
                .limit(10)
                .fetch();
    }
}
