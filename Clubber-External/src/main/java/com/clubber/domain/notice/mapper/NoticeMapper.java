package com.clubber.domain.notice.mapper;

import com.clubber.domain.notice.domain.Notice;
import com.clubber.domain.notice.dto.GetNoticeResponse;
import com.clubber.domain.notice.dto.GetNoticesAtMainResponse;
import com.clubber.global.common.page.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoticeMapper {
    public List<GetNoticesAtMainResponse> getNoticesAtMain(List<Notice> notices){
        return notices.stream()
                .map(GetNoticesAtMainResponse::from)
                .collect(Collectors.toList());
    }

    public PageResponse<GetNoticeResponse> getNotices(Page<Notice> notices){
        Page<GetNoticeResponse> noticesDto = notices.map(GetNoticeResponse::from);
        return PageResponse.of(noticesDto);
    }

}
