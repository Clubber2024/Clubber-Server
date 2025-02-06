package com.clubber.ClubberServer.integration.domain.recruitComment.service;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitCommentRepository;
import com.clubber.ClubberServer.domain.recruit.service.RecruitCommentService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RecruitCommentServiceTest extends ServiceTest {

    @Autowired
    private RecruitCommentService recruitCommentService;

    @Autowired
    private RecruitCommentRepository recruitCommentRepository;


}
