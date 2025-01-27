package com.clubber.ClubberServer.domain.notice.repository;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeCustomRepository {

    Page<Notice> queryNotice(Pageable pageable);

    List<Notice> queryTop5Notice();
}
