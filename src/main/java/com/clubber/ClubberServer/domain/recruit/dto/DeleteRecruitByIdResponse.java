package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteRecruitByIdResponse {
    private String message;
    private Long clubId;
    private Long recruitId;
    private String title;
    private String content;
    private List<String> images;
    private Long totalView;
    private LocalDateTime createdAt;


    public static DeleteRecruitByIdResponse from(Recruit recruit,List<String> images){
        return DeleteRecruitByIdResponse.builder()
                .message("해당 모집글이 삭제되었습니다.")
                .clubId(recruit.getClub().getId())
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .images(images)
                .totalView(recruit.getTotalView())
                .createdAt(recruit.getCreatedAt())
                .build();
    }

}