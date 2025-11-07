package com.clubber.domain.recruit.repository;

import com.clubber.domain.recruit.domain.Recruit;
import com.clubber.domain.recruit.domain.RecruitImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;

import static com.clubber.domain.recruit.domain.QRecruitImage.recruitImage;

@RequiredArgsConstructor
public class RecruitImageCustomRepositoryImpl implements RecruitImageCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RecruitImage> queryRecruitImages(Recruit recruit) {
        return jpaQueryFactory
            .selectFrom(recruitImage)
            .where(recruitImage.recruit.eq(recruit),
                recruitImage.isDeleted.isFalse())
            .fetch();
    }

}
