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
        return "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>클러버 인증코드</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap\" rel=\"stylesheet\" />\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Noto Sans KR', sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: white;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        img {\n" +
                "            width: 200px;\n" +
                "            height: 100px;\n" +
                "        }\n" +
                "        h2 {\n" +
                "            text-align: center;\n" +
                "            margin-top: 30px;\n" +
                "        }\n" +
                "        h4, p {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table style=\"background-color: #7bc8e0; color: white; padding: 0; width: 100%; height: 150px; margin: auto\">\n" +
                "    <tr>\n" +
                "        <td align=\"center\"><img src=\"https://image.ssuclubber.com/clubber/clubber_logo.png\" /></td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "<table style=\"width: 100%\">\n" +
                "    <tr>\n" +
                "        <td align=\"center\">\n" +
                "            <h2>이메일 인증 코드</h2>\n" +
                "            <p>안녕하세요, 클러버에서 요청하신 인증번호를 보내드립니다.</p>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\">\n" +
                "            <div\n" +
                "                    style=\"\n" +
                "                            width: 150px;\n" +
                "                            height: 50px;\n" +
                "                            border-radius: 5px;\n" +
                "                            background: #d6d6d666;\n" +
                "                            text-align: center;\n" +
                "                            padding-top: 22px;\n" +
                "                        \"\n" +
                "            >\n" +
                "                " + verificationCode + "\n" +
                "            </div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\">\n" +
                "            <p>위 인증번호 6자리를 인증번호 입력창에 입력해주세요.</p>\n" +
                "            <h4 style=\"margin-top: 0\">인증코드는 5분 후 만료됩니다.</h4>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";
    }
}