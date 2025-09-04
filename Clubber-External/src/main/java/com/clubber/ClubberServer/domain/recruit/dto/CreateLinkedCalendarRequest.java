package com.clubber.ClubberServer.domain.recruit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLinkedCalendarRequest(
        @NotNull(message = "모집글 id를 입력해주세요")
        Long recruitId,
        @NotBlank(message = "모집글 url을 입력해주세요")
        String recruitUrl
) {
}
