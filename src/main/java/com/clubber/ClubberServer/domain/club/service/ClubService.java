package com.clubber.ClubberServer.domain.club.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.dto.*;
import com.clubber.ClubberServer.domain.club.exception.*;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;



    //[중앙 동아리] - 특정 분과 소속 동아리들 반환
    public DivisionCenterDto getClubDivision(String division){
        List<Club> clubs = clubRepository.findByDivision(division);
        if (clubs.isEmpty()){
            throw new DivisionNotFoundException();
        }
        else {
            List<SimpleCenterDto> clubDtos = clubs.stream()
                    .map(club -> new SimpleCenterDto(club.getId(), club.getImageUrl(), club.getName(), club.getIntroduction()))
                    .collect(Collectors.toList());
            return new DivisionCenterDto(division, clubDtos);
        }

    }



    // 소모임 - 특정 학과 소속 소모임들 반환
    public DepartmentSmallDto getOneDepartmentClubs(String department){
        List<Club> clubs=clubRepository.findByDepartment(department);
        if (clubs.isEmpty()){
            throw new DepartmentNotFoundException();
        }
        else{
            List<SimpleCenterDto> clubDtos = clubs.stream()
                    .map(club -> new SimpleCenterDto(club.getId(), club.getImageUrl(), club.getName(), club.getIntroduction()))
                    .collect(Collectors.toList());
            return new DepartmentSmallDto(department, clubDtos);
        }
    }


    //[동아리 및 소모임] 개별 페이지 조회
    public OneClubDto getIndividualPage(Long clubId){
        Optional<Club> club=clubRepository.findById(clubId);
        return club.map(this::convertToClubDto).orElseThrow(ClubIdNotFoundException::new);
    }

    private OneClubDto convertToClubDto(Club club){
        OneClubInfoDto oneClubInfo=new OneClubInfoDto(
                club.getClubInfo().getInstagram(),
                club.getClubInfo().getLeader(),
                club.getClubInfo().getRoom(),
                club.getClubInfo().getTotalView(),
                club.getClubInfo().getActivity()
        );
        return new OneClubDto(
                club.getId(),
                club.getName(),
                club.getClubType(),
                club.getIntroduction(),
                club.getHashtag(),
                club.getDivision(),
                club.getCollege(),
                club.getDepartment(),
                club.getImageUrl(),
                oneClubInfo
        );
    }



    // 동아리명 및 소모임명으로 검색
    public SearchClubsDto getClubByName(String clubName){
        List<Club> clubs = clubRepository.findByName(clubName.toUpperCase());
        if (clubs.isEmpty()){
            throw new ClubNotFoundException();
        }
        else {
            List<SearchClubDto> clubDtos = clubs.stream()
                    .map(club -> new SearchClubDto(club.getClubType(),club.getDepartment(),club.getDivision(),club.getId(), club.getImageUrl(), club.getName(), club.getIntroduction()))
                    .collect(Collectors.toList());
            return new SearchClubsDto(clubName, clubDtos);
        }
    }


    // 특정 해시태그 반환
    public HashtagClubsDto getClubHashtag(String hashtag){
        List<Club> clubs = clubRepository.findByHashtagOrderByClubType(hashtag);
        if (clubs.isEmpty()){
            throw new HashtagNotFoundException();
        }
        else {
            List<HashtagClubDto> clubDtos = clubs.stream()
                    .map(club -> new HashtagClubDto(club.getHashtag(),club.getClubType(),club.getDivision(),club.getDepartment(), club.getId(), club.getImageUrl(), club.getName(), club.getIntroduction()))
                    .collect(Collectors.toList());
            return new HashtagClubsDto(hashtag, clubDtos);
        }

    }


    public List<PopularClubDto> getPopularClubs(){
        Pageable topTen = PageRequest.of(0, 10);
        List<Club> clubs = clubRepository.findTop10ByOrderByClubInfoTotalViewDesc(topTen);
        return clubs.stream()
                .map(club -> new PopularClubDto(club.getId(), club.getName(),club.getClubInfo().getTotalView()))
                .collect(Collectors.toList());
    }




}