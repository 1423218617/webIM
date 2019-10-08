package com.hx.webim.util;

import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
public class MailUtil {

    private static final Logger logger= LoggerFactory.getLogger(MailUtil.class);



    private static   JavaMailSender javaMailSender;


    private static  String fromMail;

    public static   boolean sendHtmlMail(String toMail){
        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(fromMail);
            helper.setTo(toMail);
            helper.setSubject("demo");
            helper.setText("777",true);
            javaMailSender.send(mimeMessage);
            return true;

        }catch (MessagingException e){
            logger.error("发送给{}的邮件失败",toMail);
            return false;
        }
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        MailUtil.javaMailSender = javaMailSender;
    }

    @Value(value = "${spring.mail.username}")
    public void setFromMail(String fromMail) {
        MailUtil.fromMail = fromMail;
    }

}
