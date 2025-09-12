package com.clubber.ClubberServer.domain.club.service;

import com.clubber.domain.domains.club.domain.College;
import com.clubber.ClubberServer.domain.club.dto.CollegeResponse;
import com.clubber.common.mapper.enums.EnumMapper;
import com.clubber.common.vo.enums.EnumMapperVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClubCategoryService {

    private final EnumMapper enumMapper;

    // [중앙 동아리] - 분과명 반환 (enum)
    public List<EnumMapperVO> getDivisions() {
        return enumMapper.get("Division")
            .stream()
            .filter(enumMapperVO -> !Objects.equals(enumMapperVO.getCode(), "ETC"))
            .toList();
    }

    // [소모임] - 전체 단과대별 소속 학과 목록 조회
    public List<CollegeResponse> getCollegesWithDepartments() {
        return Arrays.stream(College.values())
            .filter(college -> college != College.ETC)
            .map(
                college -> {
                    List<EnumMapperVO> enumMapperVOs = enumMapper.toEnumValues(
                        college.getDepartments());
                    return CollegeResponse.from(college, enumMapperVOs);
                }).toList();
    }

    // [회원가입] 단일 단과대 소속 학과 목록 조회
    public List<EnumMapperVO> getDepartmentsByCollege(College college) {
        return enumMapper.toEnumValues(college.getDepartments());
    }

    // [회원가입] 동아리 type 목록 조회
    public List<EnumMapperVO> getClubTypes() {
        return enumMapper.get("ClubType");
    }

    // [회원가입] 단과대 목록 반환
    public List<EnumMapperVO> getColleges() {
        return enumMapper.get("College");
    }

    // [해시태그] 해시태그 목록 반환
    public List<EnumMapperVO> getHashtags() {
        return enumMapper.get("Hashtag");
    }
}
