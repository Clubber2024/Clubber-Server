package com.clubber.global.infrastructure.outer.discord.message;

import com.clubber.global.infrastructure.outer.discord.dto.DiscordMessage;
import com.clubber.global.infrastructure.outer.discord.dto.DiscordMessage.Embed;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DiscordMessageFactory {

	public DiscordMessage createDiscordMessage(String title, String description, String content) {
		List<Embed> embedList = createdEmbedList(title, description);

		return DiscordMessage.builder()
			.content(content)
			.embeds(embedList)
			.build();
	}
	private static List<Embed> createdEmbedList(String title, String description) {
		return List.of(Embed
			.builder()
			.title(title)
			.description(description)
			.build());
	}
}
