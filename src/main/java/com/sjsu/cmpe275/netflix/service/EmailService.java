package com.sjsu.cmpe275.netflix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;



    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void sendInvitationForUser(String to, String subject, String text) {
        sendSimpleMessage(to, subject, text);
    }

    public void sendConfirmationForCompletion(String to, String subject, String text) {
        sendSimpleMessage(to, subject, text);
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (MailException exception) {
            logger.debug("Unable to send message");
        }
    }


}
