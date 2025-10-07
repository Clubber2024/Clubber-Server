package com.clubber.domain.club.service;

import com.clubber.domain.club.dto.*;
import com.clubber.domain.club.implement.ClubAppender;
import com.clubber.domain.club.implement.ClubReader;
import com.clubber.domain.domains.club.domain.*;
import com.clubber.domain.review.dto.GetClubReviewAgreedStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubReader clubReader;
    private final ClubAppender clubAppender;

    //[중앙 동아리] - 특정 분과 소속 동아리들 반환
    public GetClubByDivisionResponse getClubsByDivision(Division division) {
        List<Club> clubs = clubReader.findByDivision(division);

        List<GetClubIntoCardResponse> clubDtos = clubs.stream()
                    .map(GetClubIntoCardResponse::from)
                    .collect(Collectors.toList());
        return GetClubByDivisionResponse.of(division, clubDtos);
    }

    // [소모임] - 특정 학과 소속 소모임들 반환
    public DepartmentSmallDto getClubsByDepartment(Department department) {
        List<Club> clubs = clubReader.findByDepartment(department);

        List<GetClubIntoCardResponse> clubDtos = clubs.stream()
                    .map(GetClubIntoCardResponse::from)
                    .collect(Collectors.toList());
        return new DepartmentSmallDto(department, clubDtos);
    }

    //[동아리 및 소모임] 개별 페이지 조회
    @Transactional
    public GetClubResponse getClubsIndividualPage(Long clubId) {
        Club club = clubReader.findById(clubId);
        ClubInfo clubInfo = club.getClubInfo();
        clubAppender.increaseClubTotalView(clubInfo);
        return GetClubResponse.of(club, GetClubInfoResponse.from(clubInfo));
    }

    // 동아리명 및 소모임명으로 검색
    public GetClubsSearchResponse getClubsByName(String clubName) {
        List<Club> clubs = clubReader.findByName(clubName);

        List<String> clubTypes = Arrays.stream(ClubType.values())
                .map(ClubType::getTitle)
                .toList();

        Map<String, List<GetClubSearchResponse>> groupedClubs = clubs.stream()
                .collect(Collectors.groupingBy(
                        club -> club.getClubType().getTitle(),
                        () -> new TreeMap<>(Comparator.comparing(clubTypes::indexOf)),
                        Collectors.mapping(GetClubSearchResponse::from, Collectors.toList())
                ));
        return GetClubsSearchResponse.of(groupedClubs);
    }

    // 해시태그별 동아리/소모임 조회
    public GetClubsByHashTagResponse getClubsHashtag(Hashtag hashtag) {
        List<Club> clubs = clubReader.findByHashtag(hashtag);

        List<GetClubByHashTagResponse> clubDtos = clubs.stream()
                .map(GetClubByHashTagResponse::from)
                .toList();

        return GetClubsByHashTagResponse.of(hashtag, clubDtos);
    }

    public List<GetClubPopularResponse> getClubsPopular() {
        List<Club> clubs = clubReader.findPopularTopTenClubs();
        return clubs.stream()
                .map(GetClubPopularResponse::from)
                .toList();
    }

    // [한눈에 보기]
    public List<GetSummaryClubGroupResponse> getSummaryClubs() {
        List<Club> clubs = clubReader.findByClubType(ClubType.CENTER);

        return clubs.stream()
                .filter(club -> club.getDivision() != Division.ETC)
                .sorted(Comparator.comparing(Club::getDivision))
                .collect(Collectors.groupingBy(
                        Club::getDivision,
                        LinkedHashMap::new,
                        Collectors.mapping(GetSummaryClubResponse::from, Collectors.toList())
                ))
                .entrySet().stream()
                .map(clubGroup -> GetSummaryClubGroupResponse.of(clubGroup.getKey(),
                        clubGroup.getValue()))
                .collect(Collectors.toList());
    }

    // [숭실대 공식 단체]
    public GetOfficialClubGroupResponse getOfficialClubs() {
        List<Club> clubs = clubReader.findByClubType(ClubType.OFFICIAL);

        List<GetOfficialClubResponse> clubList = clubs.stream()
                .map(GetOfficialClubResponse::from)
                .collect(Collectors.toList());

        return GetOfficialClubGroupResponse.from(clubList);
    }

    // [회원가입] 동아리명 검색
    public List<GetClubsSearchForSignUpResponse> searchForSignUp(String clubName) {
        List<Club> clubs = clubReader.findByNameOrderByName(clubName);

        return clubs.stream()
                .map(GetClubsSearchForSignUpResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GetClubReviewAgreedStatusResponse getClubReviewAgreedStatus(Long clubId) {
        Club club = clubReader.findById(clubId);
        return GetClubReviewAgreedStatusResponse.from(club);
    }
}