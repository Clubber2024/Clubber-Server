package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.discord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class DiscordMessage {

    private String content;
    private List<Embed> embeds;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Embed {
        private String title;
        private String description;
    }
}
