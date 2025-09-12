package com.clubber.global.infrastructure.outer.discord.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record DiscordMessage(String content, List<Embed> embeds) {

	@Builder
	public record Embed(String title, String description) {

	}
}
