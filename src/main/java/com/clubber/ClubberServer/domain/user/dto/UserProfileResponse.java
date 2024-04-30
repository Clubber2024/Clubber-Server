package com.clubber.ClubberServer.domain.user.dto;


import com.clubber.ClubberServer.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserProfileResponse {

    private Long id;
    private String email;

    public static UserProfileResponse of(User user){
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
