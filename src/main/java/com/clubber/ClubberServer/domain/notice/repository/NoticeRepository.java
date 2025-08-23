package com.clubber.ClubberServer.domain.notice.repository;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    Page<Notice> findByOrderByIdDesc(Pageable pageable);

    List<Notice> findTop4ByOrderByIdDesc();

}
