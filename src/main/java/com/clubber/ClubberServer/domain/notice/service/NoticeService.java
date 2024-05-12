package com.clubber.ClubberServer.domain.notice.service;

import com.clubber.ClubberServer.domain.club.dto.DivisionCenterDto;
import com.clubber.ClubberServer.domain.club.dto.SimpleCenterDto;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.domain.notice.dto.NoticesDto;
import com.clubber.ClubberServer.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    public List<NoticesDto> getSortedNotices(){
        Sort sort =Sort.by(Sort.Direction.DESC,"createdAt");
        List <Notice> notices=noticeRepository.findAll(sort);
        List <NoticesDto> noticeDto = notices.stream()
                .map(notice -> new NoticesDto(notice.getId(), notice.getContent(), notice.getCreatedAt()))
                .collect(Collectors.toList());
        return noticeDto;
    }
}
