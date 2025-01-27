package com.clubber.ClubberServer.domain.notice.repository;

import static com.clubber.ClubberServer.domain.notice.domain.QNotice.notice;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class NoticeCustomRepositoryImpl implements NoticeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Notice> queryNotice(Pageable pageable) {
        List<Notice> noticeList = jpaQueryFactory
            .selectFrom(notice)
            .orderBy(notice.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(notice.count())
            .from(notice);

        return PageableExecutionUtils.getPage(noticeList, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Notice> queryTop5Notice() {
        return jpaQueryFactory
            .selectFrom(notice)
            .orderBy(notice.id.desc())
            .limit(5)
            .fetch();
    }
}