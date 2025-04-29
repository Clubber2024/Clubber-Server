package com.clubber.ClubberServer.domain.club.service;

import com.clubber.ClubberServer.domain.club.domain.*;
import com.clubber.ClubberServer.domain.club.dto.*;
import com.clubber.ClubberServer.domain.club.exception.*;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;

    //[중앙 동아리] - 특정 분과 소속 동아리들 반환
    public GetClubByDivisionResponse getClubsByDivision(Division division) {
        List<Club> clubs = clubRepository.findByDivisionAndIsDeleted(division, false);
        if (clubs.isEmpty()) {
            throw DivisionNotFoundException.EXCEPTION;
        }

        List<GetClubIntoCardResponse> clubDtos = clubs.stream()
                    .map(GetClubIntoCardResponse::from)
                    .collect(Collectors.toList());
        return GetClubByDivisionResponse.of(division, clubDtos);
    }

    // [소모임] - 특정 학과 소속 소모임들 반환
    public DepartmentSmallDto getClubsByDepartment(Department department) {
        List<Club> clubs = clubRepository.findByDepartmentAndIsDeleted(department, false);
        if (clubs.isEmpty()) {
            throw DepartmentNotFoundException.EXCEPTION;
        }

        List<GetClubIntoCardResponse> clubDtos = clubs.stream()
                    .map(GetClubIntoCardResponse::from)
                    .collect(Collectors.toList());
        return new DepartmentSmallDto(department, clubDtos);
    }

    //[동아리 및 소모임] 개별 페이지 조회
    @Transactional
    public GetClubResponse getClubsIndividualPage(Long clubId) {
        Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
                .orElseThrow(() -> ClubIdNotFoundException.EXCEPTION);

        club.validateAgreeToProvideInfo();

        club.getClubInfo().increaseTotalView();
        return GetClubResponse.of(club, GetClubInfoResponse.from(club.getClubInfo()));
    }

    // 동아리명 및 소모임명으로 검색
    public GetClubsSearchResponse getClubsByName(String clubName) {
        List<Club> clubs = clubRepository.findByNameOrderByClubType(clubName.toUpperCase());

        List<String> clubTypes = Arrays.stream(ClubType.values())
                .map(ClubType::getTitle)
                .toList();

        if (clubs.isEmpty()) {
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

    // 해시태그별 동아리/소모임 조회
    public GetClubsByHashTagResponse getClubsHashtag(Hashtag hashtag) {
        List<Club> clubs = clubRepository.findByHashtagAndIsDeletedOrderByClubType(hashtag, false);
        if (clubs.isEmpty()) {
            throw HashtagNotFoundException.EXCEPTION;
        }

        List<GetClubByHashTagResponse> clubDtos = clubs.stream()
                .map(GetClubByHashTagResponse::from)
                .toList();

        return GetClubsByHashTagResponse.of(hashtag, clubDtos);
    }

    public List<GetClubPopularResponse> getClubsPopular() {
        Pageable topTen = PageRequest.of(0, 10);
        List<Club> clubs = clubRepository.findTop10ByOrderByClubInfoTotalViewDesc(topTen);
        return clubs.stream()
                .map(GetClubPopularResponse::from)
                .toList();
    }

    // [한눈에 보기]
    public List<GetSummaryClubGroupResponse> getSummaryClubs() {
        List<Club> clubs = clubRepository.findByClubTypeAndIsDeletedFalse(ClubType.CENTER);

        return clubs.stream()
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
        ClubType officialType = ClubType.OFFICIAL;
        List<Club> clubs = clubRepository.findByClubTypeAndIsDeletedFalse(officialType);

        List<GetOfficialClubResponse> clubList = clubs.stream()
                .map(GetOfficialClubResponse::from)
                .collect(Collectors.toList());

        return GetOfficialClubGroupResponse.of(officialType, clubList);
    }

    // [회원가입] 동아리명 검색
    public List<GetClubsSearchForSignUpResponse> searchForSignUp(String clubName) {
        List<Club> clubs = clubRepository.findByNameOrderByName(clubName.toUpperCase());

        return clubs.stream()
                .map(GetClubsSearchForSignUpResponse::from)
                .collect(Collectors.toList());
    }

    /**TODO**
     * 추후 Projection으로 수정
     */
    public List<GetClubPopularResponse> getClubsPopularTemp() {
        return clubRepository.findAllOrderByTotalViewDesc();
    }
}