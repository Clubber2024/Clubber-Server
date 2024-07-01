package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.global.enummapper.EnumMapperVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CollegeDTOResponse {
    private String code;
    private String title;
    private List<EnumMapperVO> departments;

}
