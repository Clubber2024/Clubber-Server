package com.clubber.ClubberServer.domain.user.dto;


import com.clubber.ClubberServer.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUsersProfileResponse {

    @Schema(description = "유저 id", example = "1")
    private final Long id;

    @Schema(description = "유저 이메일", example = "ssuclubber@gmail.com")
    private final String email;

    public static GetUsersProfileResponse of(User user){
        return GetUsersProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
