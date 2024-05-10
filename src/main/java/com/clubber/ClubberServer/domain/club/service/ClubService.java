package com.clubber.ClubberServer.domain.club.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.dto.*;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;


    //동아리 및 소모임 정보 저장
    public Long registerClub(Club club){
        if (clubRepository.findById(club.getId()).isPresent()){ //이미 등록된 동아리,소모임인지 확인
            throw new RuntimeException("이미 존재하는 동아리 및 소모임입니다");
        }
        clubRepository.save(club);
        return club.getId();
    }


    // 중앙동아리 - 분과별로 그룹화
    public List<DivisionCenterDto> getClubsGroupedByDivision(){
        List<Club> centers=clubRepository.findByClubType("center"); //중앙 동아리 찾기
        Map<String,List<SimpleCenterDto>> oneDivision=centers.stream()
                .collect(Collectors.groupingBy(Club::getDivision,
                        Collectors.mapping(club->new SimpleCenterDto(club.getId(),club.getName(),club.getClubInfo().getContent()),
                                Collectors.toList())));
        return oneDivision.entrySet().stream()
                .map(entry->new DivisionCenterDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

    }



    // 중앙 동아리 - 특정 분과 반환
    public DivisionCenterDto getClubDivision(String division){
        List<Club> clubs = clubRepository.findByClubTypeAndDivision("center", division);
        List<SimpleCenterDto> clubDtos = clubs.stream()
                .map(club -> new SimpleCenterDto(club.getId(), club.getName(),club.getClubInfo().getContent()))
                .collect(Collectors.toList());
        return new DivisionCenterDto(division, clubDtos);

    }



    // 소모임 - 특정 단과대 반환
    public CollegeSmallDto getSmallCollege(String college){
        List<Club> clubs=clubRepository.findByCollege(college);
        Map<String,List<SimpleCenterDto>> oneDepartment=clubs.stream()
                .collect(Collectors.groupingBy(Club::getDepartment,
                        Collectors.mapping(club-> new SimpleCenterDto(club.getId(),club.getName(),club.getClubInfo().getContent()),
                                Collectors.toList())));
        List<DepartmentSmallDto> departmentDtos = oneDepartment.entrySet().stream()
                .map(entry-> new DepartmentSmallDto(entry.getKey(),entry.getValue()))
                .collect(Collectors.toList());
        return new CollegeSmallDto(college,departmentDtos);
    }



    // 소모임 - 특정 학과 반환
    public DepartmentSmallDto getOneDepartmentClubs(String department){
        List<Club> clubs=clubRepository.findByDepartment(department);
        List<SimpleCenterDto> clubDtos = clubs.stream()
                .map(club -> new SimpleCenterDto(club.getId(), club.getName(),club.getClubInfo().getContent()))
                .collect(Collectors.toList());
        return new DepartmentSmallDto(department, clubDtos);

    }



    //동아리 및 소모임 개별 페이지 조회
    public OneClubDto individualPage(Long clubId){
        Optional<Club> club=clubRepository.findById(clubId);
        return club.map(this::convertToClubDto).orElse(null);
    }

    private OneClubDto convertToClubDto(Club club){
        OneClubInfoDto oneClubInfo=new OneClubInfoDto(
                club.getClubInfo().getContent(),
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
                club.getHashtag(),
                club.getDivision(),
                club.getCollege(),
                club.getDepartment(),
                oneClubInfo
        );
    }
}