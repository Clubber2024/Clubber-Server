package com.clubber.ClubberServer.global.event.signup;

import com.clubber.ClubberServer.global.infrastructure.outer.discord.client.DiscordClient;
import com.clubber.ClubberServer.global.infrastructure.outer.discord.dto.DiscordMessage;
import com.clubber.ClubberServer.global.infrastructure.outer.discord.dto.DiscordMessage.Embed;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpAlarmEventHandler {

	private final DiscordClient discordClient;
	@Value("${discord.web-hook.sign-up}")
	private String channelId;

	@EventListener
	public void listenSignUpAlarmEvent(signUpAlarmEvent event) {
		discordClient.sendAlarm(channelId,
			createDiscordMessage(event.getClubName(), event.getContact())
		);
	}

	private DiscordMessage createDiscordMessage(String clubName, String contact) {
		List<Embed> embedList = List.of(DiscordMessage.Embed
			.builder()
			.title("[회원가입 요청]")
			.description(getDescription(clubName, contact))
			.build());

		return DiscordMessage.builder()
			.content("동아리 회원가입 승인 요청입니다.")
			.embeds(embedList)
			.build();
	}

	private String getDescription(String clubName, String contact) {
		return "[동아리명] : " + clubName + "[동아리 연락처] : " + contact;
	}
}
