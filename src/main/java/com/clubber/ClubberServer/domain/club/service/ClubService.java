package com.clubber.ClubberServer.domain.club.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.dto.*;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.notice.dto.NoticesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;

//
//    // 중앙동아리 - 분과별로 그룹화
//    public List<DivisionCenterDto> getClubsGroupedByDivision(){
//        List<Club> centers=clubRepository.findByClubType("center"); //중앙 동아리 찾기
//        Map<String,List<SimpleCenterDto>> oneDivision=centers.stream()
//                .collect(Collectors.groupingBy(Club::getDivision,
//                        Collectors.mapping(club->new SimpleCenterDto(club.getId(),club.getName(),club.getContent()),
//                                Collectors.toList())));
//        return oneDivision.entrySet().stream()
//                .map(entry->new DivisionCenterDto(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());
//
//    }


    // [중앙 동아리] - 특정 분과 소속 동아리들 반환
    public DivisionCenterDto getClubDivision(String division){
        List<Club> clubs = clubRepository.findByClubTypeAndDivision("center", division);
        List<SimpleCenterDto> clubDtos = clubs.stream()
                .map(club -> new SimpleCenterDto(club.getId(), club.getImageUrl(),club.getName(),club.getIntroduction()))
                .collect(Collectors.toList());
        return new DivisionCenterDto(division, clubDtos);

    }


//    // [소모임] - 특정 단과대 소속 소모임들 반환
//    public CollegeSmallDto getSmallCollege(String college){
//        List<Club> clubs=clubRepository.findByCollege(college);
//        Map<String,List<SimpleCenterDto>> oneDepartment=clubs.stream()
//                .collect(Collectors.groupingBy(Club::getDepartment,
//                        Collectors.mapping(club-> new SimpleCenterDto(club.getId(),club.getName(),club.getContent()),
//                                Collectors.toList())));
//        List<DepartmentSmallDto> departmentDtos = oneDepartment.entrySet().stream()
//                .map(entry-> new DepartmentSmallDto(entry.getKey(),entry.getValue()))
//                .collect(Collectors.toList());
//        return new CollegeSmallDto(college,departmentDtos);
//    }




    // 소모임 - 특정 학과 소속 소모임들 반환
    public DepartmentSmallDto getOneDepartmentClubs(String department){
        List<Club> clubs=clubRepository.findByDepartment(department);
        List<SimpleCenterDto> clubDtos = clubs.stream()
                .map(club -> new SimpleCenterDto(club.getId(), club.getImageUrl(),club.getName(),club.getIntroduction()))
                .collect(Collectors.toList());
        return new DepartmentSmallDto(department, clubDtos);

    }


    //[동아리 및 소모임] 개별 페이지 조회
    public OneClubDto getIndividualPage(Long clubId){
        Optional<Club> club=clubRepository.findById(clubId);
        return club.map(this::convertToClubDto).orElse(null);
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



    // 동아리명 및 소미임명으로 검색

    public OneClubDto getClubByName(String clubName) {
        return clubRepository.findByName(clubName)
                .map(this::convertToClubDto)
                .orElseThrow(() -> new ClubNotFoundException());
    }


    // 특정 해시태그 반환
    public HashtagDto getClubHashtag(String hashtag){
        List<Club> clubs = clubRepository.findByHashtag(hashtag);
        List<SimpleCenterDto> clubDtos = clubs.stream()
                .map(club -> new SimpleCenterDto(club.getId(),club.getImageUrl(), club.getName(),club.getIntroduction()))
                .collect(Collectors.toList());
        return new HashtagDto(hashtag,clubDtos);

    }


    //인기순위 동아리 및 소모임조회
    public List<PopularClubDto> getPopularClubs(){
        Pageable topTen= PageRequest.of(0,10);
        Page<Club> page= clubRepository.findTopClubsByTotalView(topTen);
        List<Club> clubs = page.getContent();
        List<PopularClubDto> PopularClubs = clubs.stream()
                .map(club -> new PopularClubDto(club.getId(), club.getName(),club.getClubInfo().getTotalView()))
                .collect(Collectors.toList());
        return PopularClubs;

    }

}