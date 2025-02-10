package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitCommentRepository extends JpaRepository<RecruitComment, Long> {

    List<RecruitComment> findByRecruitOrderByIdAsc(Recruit recruit);

    Optional<RecruitComment> findByIdAndRecruitAndIsDeletedFalse(Long id,Recruit recruit);

}
