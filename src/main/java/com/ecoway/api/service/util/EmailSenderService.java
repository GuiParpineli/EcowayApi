package com.ecoway.api.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;
    @Autowired
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public  void sendEmail(String toEmail, String subject, String boyd){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dev.ecoway@gmail.com");
        message.setTo(toEmail);
        message.setText(boyd);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail sent successfully ....");
    }
}
