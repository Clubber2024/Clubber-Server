package com.clubber.ClubberServer.domain.favorite.repository;

import static com.clubber.ClubberServer.domain.club.domain.QClub.club;
import static com.clubber.ClubberServer.domain.favorite.domain.QFavorite.favorite;
import static com.clubber.ClubberServer.domain.user.domain.QUser.user;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class FavoriteCustomRepositoryImpl implements FavoriteCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Favorite> queryFavoritesByUserId(Long userId) {

        return queryFactory.selectFrom(favorite)
                .join(favorite.club, club).fetchJoin()
                .where(favorite.user.id.eq(userId),
                        favorite.isDeleted.eq(false))
                .orderBy(favorite.id.desc())
                .fetch();
    }

    @Override
    public Page<Favorite> queryFavoritesPageByUserId(Long userId, Pageable pageable) {
        List<Long> ids = queryFactory.select(favorite.id)
                .from(favorite)
                .where(favorite.user.id.eq(userId),
                        favorite.isDeleted.eq(false))
                .orderBy(favorite.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Favorite> favorites = queryFactory.selectFrom(favorite)
                .join(favorite.club, club).fetchJoin()
                .where(favorite.id.in(ids))
                .orderBy(favorite.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(favorite.count())
                .from(favorite)
                .where(favorite.user.id.eq(userId),
                        favorite.isDeleted.eq(false));

        return PageableExecutionUtils.getPage(favorites, pageable, countQuery::fetchOne);

    }
}
