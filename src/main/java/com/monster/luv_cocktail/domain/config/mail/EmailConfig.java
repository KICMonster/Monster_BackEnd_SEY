package com.monster.luv_cocktail.domain.config.mail;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable;
    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private boolean starttlsRequired;
    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private int connectionTimeout;
    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private int timeout;
    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private int writeTimeout;

    public EmailConfig() {
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.host);
        mailSender.setPort(this.port);
        mailSender.setUsername(this.username);
        mailSender.setPassword(this.password);
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setJavaMailProperties(this.getMailProperties());
        return mailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.tls.protocols", "TLSv1.2");
        properties.put("mail.smtp.auth", this.auth);
        properties.put("mail.smtp.starttls.enable", this.starttlsEnable);
        properties.put("mail.smtp.starttls.required", this.starttlsRequired);
        properties.put("mail.smtp.connectiontimeout", this.connectionTimeout);
        properties.put("mail.smtp.timeout", this.timeout);
        properties.put("mail.smtp.writetimeout", this.writeTimeout);
        return properties;
    }
}