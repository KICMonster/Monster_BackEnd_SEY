package com.monster.luv_cocktail.domain.service;

import com.monster.luv_cocktail.domain.enumeration.ExceptionCode;
import com.monster.luv_cocktail.domain.exception.BusinessLogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SendEmailService {
    private static final Logger log = LoggerFactory.getLogger(SendEmailService.class);
    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail, String title, String text) {
        SimpleMailMessage emailForm = this.createEmailForm(toEmail, title, text);

        try {
            this.emailSender.send(emailForm);
        } catch (RuntimeException var6) {
            log.debug("MailService.sendEmail exception occur toEmail: {}, title: {}, text: {}", new Object[]{toEmail, title, text});
            throw new BusinessLogicException(ExceptionCode.UNABLE_TO_SEND_EMAIL);
        }
    }

    private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("thsdmsdud95@naver.com");
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);
        return message;
    }

    public SendEmailService(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
}
