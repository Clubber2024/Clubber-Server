package com.clubber.ClubberServer.global.event.exceptionalarm;

import com.clubber.ClubberServer.global.helper.SpringEnvironmentHelper;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.discord.DiscordClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.discord.DiscordMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionDiscordAlarmEventHandler {

    private final DiscordClient discordClient;
    public final SpringEnvironmentHelper springEnvironmentHelper;

    @EventListener
    public void listenExceptionAlarmEvent(ExceptionAlarmEvent event) {
        sendDiscordAlarm(event.getE(), event.getRequest());
    }

    private void sendDiscordAlarm(Exception e, WebRequest request) {
        discordClient.sendAlarm(createDiscordMessage(e, request));
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

    private String getErrorStackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private String getRequestFullPath(WebRequest webRequest) {
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        return request.getMethod() + request.getRequestURL();
    }

    private String getDescription(Exception e, WebRequest request) {
        return "발생 시간 : " + LocalDateTime.now() + "\n"
                + "요청 URL : " + getRequestFullPath(request) + "\n"
                + "에러 사항 : " + getErrorStackTrace(e).substring(0, 1000) + "\n";
    }
}
