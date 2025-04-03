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
                    helper.setText(getHtml(text), true);
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

    private String getHtml(String verificationCode) {
        return "<!DOCTYPE html>" +
                "<html lang=\"ko\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>클러버 인증코드</title>" +
                "    <style>" +
                "        .code-box {" +
                "            width: 274px;" +
                "            height: 83px;" +
                "            border-radius: 5px;" +
                "            background: #d6d6d6;" +
                "            display: flex;" +
                "            align-items: center;" +
                "            justify-content: center;" +
                "        }" +
                "        .code-text {" +
                "            margin: 0;" +
                "            font-size: 32px;" +
                "            font-weight: bold;" +
                "            letter-spacing: 2px;" +
                "            text-align: center;" +
                "            line-height: 1.2;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body style=\"font-family:'Noto Sans KR', sans-serif;margin:10px;padding:0;background-color:white;color:#333;font:11px/20px 'Roboto', sans-serif;\">" +
                "    <header style=\"background-color: #7bc8e0; color: white; width: 100%; height: 200px; display: flex; align-items: center; justify-content: center; text-align: center;\">" +
                "        <img src=\"https://image.ssuclubber.com/clubber/clubber_logo.png\" style=\"width: 315px; height: 132.82px;\" alt=\"클러버 로고\">" +
                "    </header>" +
                "    <nav style=\"display: flex; align-items: center; flex-direction: column; text-align: center;\">" +
                "        <h2 style=\"margin-top: 50px;\">이메일 인증 코드</h2>" +
                "        <p style=\"margin-bottom: 1em; font-family: 'Open Sans', sans-serif; text-align: justify;\">안녕하세요, 클러버에서 요청하신 인증번호를 보내드립니다.</p>" +
                "        <div class=\"code-box\">" +
                "            <h3 class=\"code-text\">" + verificationCode + "</h3>" +
                "        </div>" +
                "        <div style=\"margin-top: 1em; text-align: center;\">" +
                "            <p style=\"margin-bottom: 1em; font-family: 'Open Sans', sans-serif;\">위 인증번호 6자리를 인증번호 입력창에 입력해주세요.<br><strong>인증코드는 5분 후 만료됩니다.</strong></p>" +
                "        </div>" +
                "    </nav>" +
                "</body>" +
                "</html>";
    }
}
