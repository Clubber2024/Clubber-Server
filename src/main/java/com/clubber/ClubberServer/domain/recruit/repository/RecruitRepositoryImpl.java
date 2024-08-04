package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.recruit.domain.QRecruit;
import com.clubber.ClubberServer.domain.recruit.domain.QRecruitImage;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.clubber.ClubberServer.domain.recruit.domain.QRecruit.recruit;
import static com.clubber.ClubberServer.domain.recruit.domain.QRecruitImage.recruitImage;

@RequiredArgsConstructor
public class RecruitRepositoryImpl implements RecruitRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Recruit> findRecruitsWithImagesByClubId(Long clubId) {
        QRecruit recruit = QRecruit.recruit;
        QRecruitImage recruitImage = QRecruitImage.recruitImage;

        return queryFactory.select(recruit).distinct()
                .from(recruit)
                .leftJoin(recruitImage).on(recruitImage.recruit.id.eq(recruit.id))
                .where(recruit.club.id.eq(clubId).and(recruit.deleted.isFalse()))
                .fetchJoin()
                .fetch();
    }
}
