package com.clubber.ClubberServer.domain.recruit.repository;

import static com.clubber.ClubberServer.domain.recruit.domain.QRecruit.recruit;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class RecruitCustomRepositoryImpl implements RecruitCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Recruit> queryTop5Recruits() {
        return jpaQueryFactory
                .selectFrom(recruit)
                .where(recruit.isDeleted.isFalse())
                .orderBy(recruit.id.desc())
                .limit(5)
                .fetch();
    }

    @Override
    public Page<Recruit> queryRecruitsByClub(Club club, Pageable pageable) {

        List<Recruit> recruitList = jpaQueryFactory
                .selectFrom(recruit)
                .where(recruit.club.eq(club),
                        recruit.isDeleted.isFalse())
                .orderBy(recruit.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(recruit.count())
                .from(recruit)
                .where(
                        recruit.club.eq(club),
                        recruit.isDeleted.isFalse()
                );

        return PageableExecutionUtils.getPage(recruitList, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<Recruit> queryAllRecruits(Pageable pageable) {
        List<Recruit> recruitList = jpaQueryFactory
                .selectFrom(recruit)
                .where(recruit.isDeleted.isFalse())
                .orderBy(recruit.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(recruit.count())
                .from(recruit)
                .where(recruit.isDeleted.isFalse()
                );

        return PageableExecutionUtils.getPage(recruitList, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<Recruit> queryRecruitsById(Long recruitId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(recruit)
                        .where(
                                recruit.id.eq(recruitId),
                                recruit.isDeleted.isFalse()
                        )
                        .fetchOne()
        );
    }

    @Override
    public void softDeleteRecruitByClubId(Long clubId) {
        jpaQueryFactory.update(recruit)
                .set(recruit.isDeleted, true)
                .where(
                        recruit.club.id.eq(clubId),
                        recruit.club.isDeleted.eq(false)
                )
                .execute();
    }
}
