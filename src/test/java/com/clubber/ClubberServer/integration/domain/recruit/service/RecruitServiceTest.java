package com.clubber.ClubberServer.integration.domain.recruit.service;


import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class RecruitServiceTest extends ServiceTest {

    @Mock
    private RecruitRepository recruitRepository;

    @InjectMocks
    private RecruitService recruitService;

    @Autowired
    private ObjectMapper objectMapper;

}
