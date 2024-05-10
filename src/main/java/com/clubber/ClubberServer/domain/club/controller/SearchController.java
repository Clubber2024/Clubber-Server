package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.dto.DivisionCenterDto;
import com.clubber.ClubberServer.domain.club.dto.HashtagDto;
import com.clubber.ClubberServer.domain.club.dto.OneClubDto;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search")
public class SearchController {

    private final ClubRepository clubRepository;
    private final ClubService clubService;


    // hashtag 기반 검색
    @GetMapping(params="hashtag")
    public ResponseEntity<HashtagDto> searchByHashtag(@RequestParam("hashtag")String hashtag){
        HashtagDto curr=clubService.getClubHashtag(hashtag);
        return ResponseEntity.ok(curr);

    }

    // 동아리명 or 소모임명 기반 검색
    @GetMapping(params="clubName")
    public ResponseEntity<OneClubDto> searchByClubName(@RequestParam("clubName")String clubName){
        OneClubDto curr=clubService.getClubByName(clubName);
        return ResponseEntity.ok(curr);
    }


}
