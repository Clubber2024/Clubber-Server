package com.clubber.ClubberServer.global.infrastructure.outer.mail;

import com.clubber.ClubberServer.global.infrastructure.outer.mail.exception.MailNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.CLUBBER_EMAIL;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendAsync(String to, String subject, String text) {
        send(to, subject, text);
    }

    public void send(String to, String subject, String text) {
        MimeMessagePreparator messagePreparator =
                mimeMessage -> {
                    final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setFrom(CLUBBER_EMAIL);
                    helper.setTo(to);
                    helper.setSubject(subject);
                    helper.setText(text);
                };

        sendMailRetry(3, messagePreparator);
    }

    public void sendMailRetry(int retryCount, MimeMessagePreparator messagePreparator) {
        if (retryCount < 0) {
            throw MailNotSentException.EXCEPTION;
        }

        try {
            mailSender.send(messagePreparator);
        } catch (Exception e) {
            log.error("메일 전송 오류 : " + e);
            sendMailRetry(retryCount - 1, messagePreparator);
        }
    }
}
