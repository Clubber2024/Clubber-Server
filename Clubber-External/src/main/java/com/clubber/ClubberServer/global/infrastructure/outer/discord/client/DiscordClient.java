package com.clubber.ClubberServer.global.infrastructure.outer.discord.client;

import com.clubber.ClubberServer.global.config.feign.FeignConfig;
import com.clubber.ClubberServer.global.infrastructure.outer.discord.dto.DiscordMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(
	name = "discord-client",
	url = "https://discordapp.com/api/webhooks",
	configuration = FeignConfig.class)
public interface DiscordClient {

	@PostMapping(value = "/{channelId}")
	void sendAlarm(@PathVariable String channelId, @RequestBody DiscordMessage discordMessage);
}
