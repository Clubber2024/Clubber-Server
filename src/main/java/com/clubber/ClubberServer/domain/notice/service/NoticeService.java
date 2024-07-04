package com.clubber.ClubberServer.domain.notice.service;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.domain.notice.dto.NoticesDto;
import com.clubber.ClubberServer.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    public List<NoticesDto> getSortedNotices(){
        List <Notice> notices=noticeRepository.findByOrderByIdDesc();
        List <NoticesDto> noticeDto = notices.stream()
                .map(notice -> new NoticesDto(notice.getId(), notice.getContent(), notice.getCreatedAt()))
                .collect(Collectors.toList());
        return noticeDto;
    }
}
