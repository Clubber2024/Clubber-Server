package com.clubber.ClubberServer.domain.notice.repository;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeCustomRepository {

}
