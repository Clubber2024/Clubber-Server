package com.clubber.ClubberServer.domain.club.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.domain.club.domain.Division;
import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import com.clubber.ClubberServer.domain.club.dto.CollegeResponse;
import com.clubber.ClubberServer.domain.club.dto.DepartmentSmallDto;
import com.clubber.ClubberServer.domain.club.dto.GetClubByDivisionResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubByHashTagResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubInfoResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubIntoCardResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubPopularResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubSearchResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubsByHashTagResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubsSearchForSignUpResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubsSearchResponse;
import com.clubber.ClubberServer.domain.club.dto.GetOfficialClubGroupResponse;
import com.clubber.ClubberServer.domain.club.dto.GetOfficialClubResponse;
import com.clubber.ClubberServer.domain.club.dto.GetSummaryClubGroupResponse;
import com.clubber.ClubberServer.domain.club.dto.GetSummaryClubResponse;
import com.clubber.ClubberServer.domain.club.exception.ClubIdNotFoundException;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.exception.DepartmentNotFoundException;
import com.clubber.ClubberServer.domain.club.exception.DivisionNotFoundException;
import com.clubber.ClubberServer.domain.club.exception.HashtagNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.global.mapper.enums.EnumDefaultMapper;
import com.clubber.ClubberServer.global.mapper.enums.EnumImageMapper;
import com.clubber.ClubberServer.global.vo.enums.EnumImageMapperVO;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    private final EnumImageMapper enumImageMapper;

    private final EnumDefaultMapper enumDefaultMapper;

    //[중앙 동아리] - 특정 분과 소속 동아리들 반환
    @Transactional(readOnly = true)
    public GetClubByDivisionResponse getClubsByDivision(Division division) {
        List<Club> clubs = clubRepository.findByDivisionAndIsDeleted(division, false);
        if (clubs.isEmpty()) {
            throw DivisionNotFoundException.EXCEPTION;
        } else {
            List<GetClubIntoCardResponse> clubDtos = clubs.stream()
                .map(club -> GetClubIntoCardResponse.from(club))
                .collect(Collectors.toList());

            return GetClubByDivisionResponse.of(division, clubDtos);
        }

    }

    // [소모임] - 특정 학과 소속 소모임들 반환
    @Transactional(readOnly = true)
    public DepartmentSmallDto getClubsByDepartment(Department department) {
        List<Club> clubs = clubRepository.findByDepartmentAndIsDeleted(department, false);
        if (clubs.isEmpty()) {
            throw DepartmentNotFoundException.EXCEPTION;
        } else {
            List<GetClubIntoCardResponse> clubDtos = clubs.stream()
                .map(club -> GetClubIntoCardResponse.from(club))
                .collect(Collectors.toList());
            return new DepartmentSmallDto(department, clubDtos);
        }
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
    @Transactional(readOnly = true)
    public GetClubsSearchResponse getClubsByName(String clubName) {
        List<Club> clubs = clubRepository.findByName(clubName.toUpperCase());

        List<String> clubTypes = Arrays.stream(ClubType.values())
            .map(ClubType::getTitle)
            .collect(Collectors.toList());

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


    // 특정 해시태그 반환
    @Transactional(readOnly = true)
    public GetClubsByHashTagResponse getClubsHashtag(Hashtag hashtag) {
        List<Club> clubs = clubRepository.findByHashtagAndIsDeletedOrderByClubType(hashtag, false);

        if (clubs.isEmpty()) {
            throw HashtagNotFoundException.EXCEPTION;
        }

        List<GetClubByHashTagResponse> clubDtos = clubs.stream()
            .map(club -> GetClubByHashTagResponse.from(club))
            .collect(Collectors.toList());

        return GetClubsByHashTagResponse.of(hashtag, clubDtos);

    }

    @Transactional(readOnly = true)
    public List<GetClubPopularResponse> getClubsPopularTemp() {
        return clubRepository.findAllOrderByTotalViewDesc();
    }

    @Transactional(readOnly = true)
    public List<GetClubPopularResponse> getClubsPopular() {
        Pageable topTen = PageRequest.of(0, 10);
        List<Club> clubs = clubRepository.findTop10ByOrderByClubInfoTotalViewDesc(topTen);
        return clubs.stream()
            .map(club -> GetClubPopularResponse.from(club))
            .collect(Collectors.toList());
    }

    // [해시태그] 해시태그 목록 반환 (enum)
    public List<EnumImageMapperVO> getClubsTotalHashtags() {
        return enumImageMapper.get("Hashtag");
    }

    // [중앙 동아리] - 분과명 반환 (enum)
    public List<EnumMapperVO> getDivisionNames() {
        return enumDefaultMapper.get("Division");
    }


    // [소모임] - 단과대 & 학과명 반환 (enum)
    public List<CollegeResponse> getCollegesWithDepartments() {
        List<EnumMapperVO> colleges = enumDefaultMapper.get("College");

        return colleges.stream()
            .map(college -> CollegeResponse.from(College.valueOf(college.getCode())))
            .collect(Collectors.toList());
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
        List<Club> clubs = clubRepository.findByClubTypeAndIsDeletedFalse(ClubType.OFFICIAL);

        List<GetOfficialClubResponse> clubList = clubs.stream()
            .map(GetOfficialClubResponse::from)
            .collect(Collectors.toList());

        return GetOfficialClubGroupResponse.of(ClubType.OFFICIAL, clubList);
    }


    // [회원가입] 동아리명 검색
    @Transactional(readOnly = true)
    public List<GetClubsSearchForSignUpResponse> searchForSignUp(String clubName) {
        List<Club> clubs = clubRepository.findByNameSorted(clubName.toUpperCase());

        return clubs.stream()
            .map(GetClubsSearchForSignUpResponse::from)
            .collect(Collectors.toList());
    }

}