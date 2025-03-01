package com.clubber.ClubberServer.global.event.exceptionalarm;

import com.clubber.ClubberServer.global.infrastructure.outer.discord.client.DiscordClient;
import com.clubber.ClubberServer.global.infrastructure.outer.discord.dto.discord.DiscordMessage;
import jakarta.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionDiscordAlarmEventHandler {

	@Value("${discord.web-hook.server-error}")
	private String channelId;

	private final DiscordClient discordClient;

	@EventListener
	public void listenExceptionAlarmEvent(ExceptionAlarmEvent event) {
		sendDiscordAlarm(event.getE(), event.getRequest());
	}

	private void sendDiscordAlarm(Exception e, WebRequest request) {
		discordClient.sendAlarm(channelId, createDiscordMessage(e, request));
	}

	private DiscordMessage createDiscordMessage(Exception e, WebRequest request) {
		List<DiscordMessage.Embed> embedList = List.of(DiscordMessage.Embed
			.builder()
			.title("[서버 에러 발생]")
			.description(getDescription(e, request))
			.build());

		return DiscordMessage.builder()
			.content("에러 발생 내용")
			.embeds(embedList)
			.build();
	}

	private String getDescription(Exception e, WebRequest request) {
		return "발생 시간 : " + LocalDateTime.now() + "\n"
			+ "요청 URL : " + getRequestFullPath(request) + "\n"
			+ "에러 사항 : " + getErrorStackTrace(e).substring(0, 1000) + "\n";
	}

	private String getRequestFullPath(WebRequest webRequest) {
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		return request.getMethod() + request.getRequestURL();
	}

	private String getErrorStackTrace(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
}
