package com.technova.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendScheduleConfirmation(String toEmail, String name, String scheduleTime) {
        String subject = "Your Demo is Scheduled";
        String content = String.format(
                "Hello %s,<br><br>Your demo has been scheduled for <strong>%s</strong>.<br><br>Thank you!",
                name, scheduleTime
        );

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true); // HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            // Log the exception
            e.printStackTrace();
        }
    }
}
