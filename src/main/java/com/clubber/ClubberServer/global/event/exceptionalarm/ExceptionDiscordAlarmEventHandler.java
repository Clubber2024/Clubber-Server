package com.clubber.ClubberServer.global.event.exceptionalarm;

import com.clubber.ClubberServer.global.infrastructure.outer.discord.client.DiscordClient;
import com.clubber.ClubberServer.global.infrastructure.outer.discord.dto.DiscordMessage;
import com.clubber.ClubberServer.global.infrastructure.outer.discord.message.DiscordMessageFactory;
import jakarta.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
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

	private final DiscordClient discordClient;
	private final DiscordMessageFactory discordMessageFactory;
	@Value("${discord.web-hook.server-error}")
	private String channelId;

	@EventListener
	public void listenExceptionAlarmEvent(ExceptionAlarmEvent event) {
		String description = getDescription(event.getE(), event.getRequest());
		DiscordMessage discordMessage = discordMessageFactory.createDiscordMessage("[서버 에러 발생]",
			description, "에러 발생 내용");
		discordClient.sendAlarm(channelId, discordMessage);
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
