package com.clubber.ClubberServer.domain.notice.mapper;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.domain.notice.dto.GetNoticeResponse;
import com.clubber.ClubberServer.domain.notice.dto.GetNoticesAtMainResponse;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class NoticeMapper {

    public List<GetNoticesAtMainResponse> getNoticesAtMain(List<Notice> notices) {
        return notices.stream()
            .map(GetNoticesAtMainResponse::from)
            .collect(Collectors.toList());
    }

    public PageResponse<GetNoticeResponse> getNotices(Page<Notice> notices) {
        Page<GetNoticeResponse> noticesDto = notices.map(GetNoticeResponse::from);
        return PageResponse.of(noticesDto);
    }

}
