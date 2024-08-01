package com.clubber.ClubberServer.domain.admin.repository;

import static com.clubber.ClubberServer.domain.admin.domain.QAdmin.admin;
import static com.clubber.ClubberServer.domain.club.domain.QClub.club;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.QAdmin;
import com.clubber.ClubberServer.domain.club.domain.QClub;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;


@RequiredArgsConstructor
public class AdminCustomRespositoryImpl implements AdminCustomRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public Admin queryAdminById(Long adminId) {
        return queryFactory.selectFrom(admin)
                .from(admin)
                .join(admin.club, club).fetchJoin()
                .where(admin.id.eq(adminId))
                .fetchOne();
    }
}
