package com.clubber.ClubberServer.domain.notice.service;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.domain.notice.dto.GetNoticeResponse;
import com.clubber.ClubberServer.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    public List<GetNoticeResponse> getNotices(){
        List <Notice> notices=noticeRepository.findByOrderByIdDesc();
        List <GetNoticeResponse> noticeDto = notices.stream()
                .map(notice -> GetNoticeResponse.from(notice))
                .collect(Collectors.toList());
        return noticeDto;
    }
}
