package com.clubber.ClubberServer.domain.club.repository;

import com.clubber.ClubberServer.domain.club.dto.GetClubPopularResponse;

import java.util.List;

public interface ClubCustomRepository {

    List<GetClubPopularResponse> findAllOrderByTotalViewDesc();
}
