package com.clubber.ClubberServer.global.infrastructure.outer.discord.message;

import com.clubber.ClubberServer.global.infrastructure.outer.discord.dto.DiscordMessage;
import com.clubber.ClubberServer.global.infrastructure.outer.discord.dto.DiscordMessage.Embed;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DiscordMessageFactory {

	private DiscordMessage createDiscordMessage(String title, String description, String content) {
		List<Embed> embedList = List.of(DiscordMessage.Embed
			.builder()
			.title(title)
			.description(description)
			.build());

		return DiscordMessage.builder()
			.content(content)
			.embeds(embedList)
			.build();
	}
}
