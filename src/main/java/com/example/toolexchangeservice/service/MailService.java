package com.example.toolexchangeservice.service;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final FreeMarkerConfigurer freemarkerConfigurer;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public MailService(JavaMailSender mailSender, FreeMarkerConfigurer freemarkerConfigurer) {
        this.mailSender = mailSender;
        this.freemarkerConfigurer = freemarkerConfigurer;
    }

    @Async
    public void sendMail(String to, String advertTitle, String fromUsername, Date suggestedTimeframe,
                         String messageText) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);


            messageHelper.setFrom(this.fromEmail);
            messageHelper.setTo(to);
            messageHelper.setSubject("Dobili ste podudu za vaš oglas");

            Map<String, Object> templateMap = new HashMap<>();
            templateMap.put("advertTitle", advertTitle);
            templateMap.put("fromUsername", fromUsername);
            templateMap.put("suggestedTimeframe", suggestedTimeframe.toString());
            templateMap.put("message", messageText);


            String content = FreeMarkerTemplateUtils.processTemplateIntoString(
                    this.freemarkerConfigurer.getConfiguration().getTemplate("mail-template.flt"), templateMap);
            messageHelper.setText(content, true);

            this.mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Exception whilst sending mail", e);
        }
    }
}
