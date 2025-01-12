package com.clubber.ClubberServer.domain.notice.service;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.domain.notice.dto.GetNoticeResponse;
import com.clubber.ClubberServer.domain.notice.dto.GetNoticesAtMainResponse;
import com.clubber.ClubberServer.domain.notice.exception.NoticeNotFoundException;
import com.clubber.ClubberServer.domain.notice.repository.NoticeRepository;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;


    @Transactional(readOnly = true)
    public List<GetNoticesAtMainResponse> getNoticesAtMain(){
        List<Notice> notices=noticeRepository.findTop5ByOrderByIdDesc();
        List <GetNoticesAtMainResponse> noticeDto = notices.stream()
                .map(notice -> GetNoticesAtMainResponse.from(notice))
                .collect(Collectors.toList());
        return noticeDto;
    }

    @Transactional(readOnly = true)
    public PageResponse<GetNoticeResponse> getNotices(Pageable pageable){
        Page<Notice> notices=noticeRepository.findByOrderByIdDesc(pageable);
        Page<GetNoticeResponse> noticesDto = notices.map(GetNoticeResponse::from);

        return PageResponse.of(noticesDto);
    }

    @Transactional
    public GetNoticeResponse getNoticesDetail(Long noticeId){
       Notice notice=noticeRepository.findById(noticeId)
               .orElseThrow(()-> NoticeNotFoundException.EXCEPTION);
       notice.increaseTotalView();

       return GetNoticeResponse.from(notice);
    }
}
