package com.clubber.global.event.exceptionalarm.async;

import com.clubber.global.infrastructure.outer.discord.client.DiscordClient;
import com.clubber.global.infrastructure.outer.discord.dto.DiscordMessage;
import com.clubber.global.infrastructure.outer.discord.message.DiscordMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@Component
@RequiredArgsConstructor
public class AsyncExceptionAlaramHandler {
    @Value("${discord.web-hook.server-error}")
    private String channelId;

    private final DiscordClient discordClient;

    private final DiscordMessageFactory discordMessageFactory;

    @EventListener
    public void publishAsyncExceptionAlarm(AsyncExceptionAlaramEvent event) {
        String description = getErrorStackTrace((Exception) event.getException());
        DiscordMessage discordMessage = discordMessageFactory.createDiscordMessage("비동기 작업 에러 발생", description, event.getMethod().toString());
        discordClient.sendAlarm(channelId, discordMessage);
    }

    private String getErrorStackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
