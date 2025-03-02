package com.clubber.ClubberServer.global.event.signup;

import com.clubber.ClubberServer.global.infrastructure.outer.discord.client.DiscordClient;
import com.clubber.ClubberServer.global.infrastructure.outer.discord.dto.DiscordMessage;
import com.clubber.ClubberServer.global.infrastructure.outer.discord.message.DiscordMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpAlarmEventHandler {

	private final DiscordClient discordClient;
	private final DiscordMessageFactory discordMessageFactory;
	@Value("${discord.web-hook.sign-up}")
	private String channelId;

	@EventListener
	public void listenSignUpAlarmEvent(signUpAlarmEvent event) {
		String description = getDescription(event.getClubName(), event.getContact());
		DiscordMessage discordMessage = discordMessageFactory.createDiscordMessage("[회원가입 요청]",
			description, "동아리 회원가입 승인 요청입니다");
		discordClient.sendAlarm(channelId, discordMessage);
	}

	private String getDescription(String clubName, String contact) {
		return "[동아리명] : " + clubName + "[동아리 연락처] : " + contact;
	}
}
