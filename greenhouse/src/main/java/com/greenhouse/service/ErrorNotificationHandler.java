package com.greenhouse.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ErrorNotificationHandler {

	private final JavaMailSender javaMailSender;
	@Value("${email.address}")
	private String email;
	@Value("${email.subject}")
	private String subject;

	public void sendMail(String body) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
				mimeMessage.setFrom(new InternetAddress("noreply@gmail.com"));
				mimeMessage.setSubject(subject);
				mimeMessage.setText(body);

			}
		};
		javaMailSender.send(preparator);
		log.info("Email was sent to : " + email);

	}
}
