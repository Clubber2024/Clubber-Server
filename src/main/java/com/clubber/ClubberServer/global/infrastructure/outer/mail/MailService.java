package com.clubber.ClubberServer.global.infrastructure.outer.mail;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.CLUBBER_EMAIL;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	public void send(String to, String subject, String text) {
		MimeMessagePreparator messagePreparator =
			mimeMessage -> {
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom(CLUBBER_EMAIL);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(text);
			};
		mailSender.send(messagePreparator);
	}
}
