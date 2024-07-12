package com.clubber.ClubberServer.domain.notice.repository;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    List<Notice> findByOrderByIdDesc();
}
