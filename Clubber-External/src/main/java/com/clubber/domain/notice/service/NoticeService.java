package com.clubber.domain.notice.service;

import com.clubber.domain.notice.domain.Notice;
import com.clubber.domain.notice.dto.GetNoticeResponse;
import com.clubber.domain.notice.dto.GetNoticesAtMainResponse;
import com.clubber.domain.notice.exception.NoticeNotFoundException;
import com.clubber.domain.notice.mapper.NoticeMapper;
import com.clubber.domain.notice.repository.NoticeRepository;
import com.clubber.global.common.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;


    @Transactional(readOnly = true)
    public List<GetNoticesAtMainResponse> getNoticesAtMain(){
        List<Notice> notices=noticeRepository.findTop4ByOrderByIdDesc();
        return noticeMapper.getNoticesAtMain(notices);
    }

    @Transactional(readOnly = true)
    public PageResponse<GetNoticeResponse> getNotices(Pageable pageable){
        Page<Notice> notices=noticeRepository.findByOrderByIdDesc(pageable);
        return noticeMapper.getNotices(notices);
    }

    @Transactional
    public GetNoticeResponse getNoticesDetail(Long noticeId){
       Notice notice=noticeRepository.findById(noticeId)
               .orElseThrow(()-> NoticeNotFoundException.EXCEPTION);
       notice.increaseTotalView();
       return GetNoticeResponse.from(notice);
    }
}
