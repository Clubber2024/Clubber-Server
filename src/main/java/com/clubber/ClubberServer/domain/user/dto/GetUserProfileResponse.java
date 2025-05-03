package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetUserProfileResponse(
        @Schema(description = "유저 id", example = "1")
        Long id,
        @Schema(description = "유저 이메일", example = "ssuclubber@gmail.com")
        String email
) {
    public static GetUserProfileResponse of(User user) {
        return GetUserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
