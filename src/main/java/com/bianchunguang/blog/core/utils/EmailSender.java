package com.bianchunguang.blog.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Map;

@Component
public class EmailSender {

    private static final Logger LOG = LoggerFactory.getLogger(EmailSender.class);

    private @Autowired SpringTemplateEngine springThymeleafTemplateEngine;
    private @Autowired JavaMailSenderImpl mailSender;

    public boolean sendEmail(final String subject, final String[] mailTo, final String mailTemplateFile, final Map<String, Object> mailContentVariablesMap) {

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), "UTF-8");
            helper.setSubject(subject);
            helper.setFrom(mailSender.getUsername());
            helper.setTo(mailTo);
            Context context = new Context();
            context.setVariables(mailContentVariablesMap);
            String text = springThymeleafTemplateEngine.process(mailTemplateFile, context);
            helper.setText(text, true);

            mailSender.send(helper.getMimeMessage());
            return true;

        } catch (Exception e) {
            LOG.error("邮件发送异常", e);
            return false;
        }
    }

    public boolean sendEmail(final String subject, final String mailTo, final String mailTemplateFile, final Map<String, Object> mailContentVariablesMap) {
        return sendEmail(subject, new String[]{mailTo}, mailTemplateFile, mailContentVariablesMap);
    }

}