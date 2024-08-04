package com.clubber.ClubberServer.domain.recruit.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecruitImageRepositoryImpl implements RecruitImageRepositoryCustom{
    private final JPAQueryFactory queryFactory;
}
