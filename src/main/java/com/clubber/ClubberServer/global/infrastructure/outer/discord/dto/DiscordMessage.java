package com.clubber.ClubberServer.global.infrastructure.outer.discord.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
