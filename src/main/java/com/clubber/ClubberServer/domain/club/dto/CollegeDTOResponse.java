package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.global.enummapper.EnumMapperVO;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CollegeDTOResponse {
    private String code;
    private String title;
    private List<EnumMapperVO> departments;

}
