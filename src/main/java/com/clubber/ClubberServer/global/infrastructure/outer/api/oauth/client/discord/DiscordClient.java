package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.discord;

import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.config.DiscordFeignConfig;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.discord.DiscordMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "discord-client",
        url = "{discord-webhook-url}",
        configuration = DiscordFeignConfig.class)
interface DiscordClient {

    @PostMapping
    void sendAlarm(@RequestBody DiscordMessage discordMessage);
}
