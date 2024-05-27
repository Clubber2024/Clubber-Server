package com.clubber.ClubberServer.domain.user.dto;


import com.clubber.ClubberServer.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserProfileResponse {

    @Schema(description = "유저 id", example = "1")
    private Long id;

    @Schema(description = "유저 이메일", example = "ssuclubber@gmail.com")
    private String email;

    public static UserProfileResponse of(User user){
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
