package com.project.officequiz.service;

import com.project.officequiz.AppConstants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.emailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public Map<String, Object> fetchEmailHtmlContent(String userName,String activationCode) {
        Map<String, Object> templateModel = new HashMap<>();
        //templateModel.put("bodyText", AppConstants.REGISTRATION_WELCOME_TEXT);
        templateModel.put("userName", userName);
        templateModel.put("buttonText", AppConstants.EMAIL_VERIFICATION_LABEL);
        templateModel.put("buttonUrl", AppConstants.REGISTRATION_PAGE_URL+activationCode);
        return templateModel;
    }

    public void sendEmail(String recipientEmail, String subject, String emailContent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(emailContent);
        emailSender.send(message);
    }
    @Async
    public void sendEmailUsingTemplate(String userName,String recipientEmail,String activationCode) throws MessagingException {
        Map<String, Object> templateModel = fetchEmailHtmlContent(userName,activationCode);
        Context context = new Context();
        context.setVariables(templateModel);
        String htmlContent = templateEngine.process("email-template", context);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(recipientEmail);
        helper.setSubject(AppConstants.REGISTRATION_WELCOME_SUBJECT);
        helper.setText(htmlContent, true);

        helper.addInline("logoImage", new ClassPathResource("static/images/logo.png"));
//        helper.addInline("officeTriviaImage", new ClassPathResource("static/images/img.png"));

        emailSender.send(message);
    }
}
