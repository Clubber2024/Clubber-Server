package com.clubber.ClubberServer.global.infrastructure.outer.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	public void send(String from, String to, String subject) {
		MimeMessagePreparator messagePreparator =
			mimeMessage -> {
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom(from);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText("example");
			};
		mailSender.send(messagePreparator);
	}
}
