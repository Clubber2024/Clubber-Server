package com.clubber.ClubberServer.domain.club.service;

import com.clubber.ClubberServer.domain.club.domain.*;
import com.clubber.ClubberServer.domain.club.dto.*;
import com.clubber.ClubberServer.domain.club.exception.*;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.global.enummapper.EnumMapper;
import com.clubber.ClubberServer.global.enummapper.EnumMapperVO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    private final EnumMapper enumMapper;


    //[중앙 동아리] - 특정 분과 소속 동아리들 반환
    public GetClubByDivisionResponse getClubsByDivision(Division division){
        List<Club> clubs = clubRepository.findByDivisionAndIsDeleted(division, false);
        if (clubs.isEmpty()){
            throw DivisionNotFoundException.EXCEPTION;
        }
        else {
            List<GetClubIntoCardResponse> clubDtos = clubs.stream()
                    .map(club -> GetClubIntoCardResponse.from(club))
                    .collect(Collectors.toList());

            return GetClubByDivisionResponse.of(division,clubDtos);
        }

    }



    // [소모임] - 특정 학과 소속 소모임들 반환
    public DepartmentSmallDto getClubsByDepartment(Department department){
        List<Club> clubs=clubRepository.findByDepartmentAndIsDeleted(department, false);
        if (clubs.isEmpty()){
            throw DepartmentNotFoundException.EXCEPTION;
        }
        else{
            List<GetClubIntoCardResponse> clubDtos = clubs.stream()
                    .map(club -> GetClubIntoCardResponse.from(club))
                    .collect(Collectors.toList());
            return new DepartmentSmallDto(department, clubDtos);
        }
    }

    //[동아리 및 소모임] 개별 페이지 조회
    @Transactional
    public GetClubResponse getClubsIndividualPage(Long clubId){
        Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
            .orElseThrow(() -> ClubIdNotFoundException.EXCEPTION);

        club.validateAgreeToProvideInfo();

        club.getClubInfo().increaseTotalView();
        return GetClubResponse.of(club,GetClubInfoResponse.from(club.getClubInfo()));

    }



    // 동아리명 및 소모임명으로 검색
    public GetClubsSearchResponse getClubsByName(String clubName){
        List<Club> clubs = clubRepository.findByName(clubName.toUpperCase());

        List<String> clubTypes = Arrays.stream(ClubType.values())
                .map(ClubType::getTitle)
                .collect(Collectors.toList());

        if (clubs.isEmpty()){
            throw ClubNotFoundException.EXCEPTION;
        }

        Map<String, List<GetClubSearchResponse>> groupedClubs = clubs.stream()
                .collect(Collectors.groupingBy(
                        club -> club.getClubType().getTitle(),
                        () -> new TreeMap<>(Comparator.comparing(clubTypes::indexOf)),
                        Collectors.mapping(GetClubSearchResponse::from, Collectors.toList())
                ));
        return GetClubsSearchResponse.of(groupedClubs);
    }


    // 특정 해시태그 반환
    public GetClubsByHashTagResponse getClubsHashtag(Hashtag hashtag){
        List<Club> clubs = clubRepository.findByHashtagAndIsDeletedOrderByClubType(hashtag, false);

        if (clubs.isEmpty()){
            throw HashtagNotFoundException.EXCEPTION;
        }

        List<GetClubByHashTagResponse> clubDtos = clubs.stream()
                .map(club->GetClubByHashTagResponse.from(club))
                .collect(Collectors.toList());

        return GetClubsByHashTagResponse.of(hashtag,clubDtos);

    }


    public List<GetClubPopularResponse> getClubsPopular(){
        Pageable topTen = PageRequest.of(0, 10);
        List<Club> clubs = clubRepository.findTop10ByOrderByClubInfoTotalViewDesc(topTen);
        return clubs.stream()
                .map(club -> GetClubPopularResponse.from(club))
                .collect(Collectors.toList());
    }

    // [해시태그] 해시태그 목록 반환 (enum)
    public List<EnumMapperVO> getClubsTotalHashtags() {
        List<EnumMapperVO> hashtagVOs = enumMapper.get("Hashtag");
        return hashtagVOs.subList(0, hashtagVOs.size() - 1);
    }


    // [중앙 동아리] - 분과명 반환 (enum)
    public List<EnumMapperVO> getDivisionNames(){
        return enumMapper.get("Division");
    }


    // [소모임] - 단과대 & 학과명 반환 (enum)
    public List<CollegeResponse> getCollegesWithDepartments() {
        List<EnumMapperVO> colleges = enumMapper.get("College");

        return colleges.stream()
                .map(college -> CollegeResponse.from(College.valueOf(college.getCode())))
                .collect(Collectors.toList());
    }

}