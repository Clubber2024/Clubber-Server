package com.clubber.ClubberServer.global.infrastructure.outer.exception.client.discord;

import com.clubber.ClubberServer.global.infrastructure.outer.exception.config.DiscordFeignConfig;
import com.clubber.ClubberServer.global.infrastructure.outer.exception.dto.discord.DiscordMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(
        name = "discord-client",
        url = "${discord.web-hook}",
        configuration = DiscordFeignConfig.class)
public interface DiscordClient {

    @PostMapping
    void sendAlarm(@RequestBody DiscordMessage discordMessage);
}
