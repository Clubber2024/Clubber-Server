package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.dto.DivisionCenterDto;
import com.clubber.ClubberServer.domain.club.dto.HashtagDto;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search")
public class SearchController {

    private final ClubRepository clubRepository;
    private final ClubService clubService;


    // hashtag 기반 검색
    @GetMapping(params="hashtag")
    public ResponseEntity<HashtagDto> getCentersByDivision(@RequestParam("hashtag")String hashtag){
        HashtagDto curr=clubService.getClubHashtag(hashtag);
        return ResponseEntity.ok(curr);

    }

}
