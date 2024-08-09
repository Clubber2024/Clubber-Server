package com.clubber.ClubberServer.domain.notice.service;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.domain.notice.dto.GetNoticeResponse;
import com.clubber.ClubberServer.domain.notice.dto.GetNoticesAtMainResponse;
import com.clubber.ClubberServer.domain.notice.exception.NoticeNotFoundException;
import com.clubber.ClubberServer.domain.notice.repository.NoticeRepository;
import com.clubber.ClubberServer.global.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;


    public List<GetNoticesAtMainResponse> getNoticesAtMain(){
        List<Notice> notices=noticeRepository.findTop5ByOrderByIdDesc();
        List <GetNoticesAtMainResponse> noticeDto = notices.stream()
                .map(notice -> GetNoticesAtMainResponse.from(notice))
                .collect(Collectors.toList());
        return noticeDto;
    }


    public PageResponse<GetNoticeResponse> getNotices(Pageable pageable){
        Page<Notice> notices=noticeRepository.findByOrderByIdDesc(pageable);
        List <GetNoticeResponse> noticeDto = notices.stream()
                .map(notice -> GetNoticeResponse.from(notice))
                .collect(Collectors.toList());

        return PageResponse.of(new PageImpl<>(noticeDto, pageable, notices.getTotalElements()));
    }

    public GetNoticeResponse getNoticesDetail(Long noticeId){
       Notice notice=noticeRepository.findById(noticeId)
               .orElseThrow(()-> NoticeNotFoundException.EXCEPTION);

       return GetNoticeResponse.from(notice);
    }
}
